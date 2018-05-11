package com.abaffy.dailyflights.ui.list

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.abaffy.dailyflights.R
import com.abaffy.dailyflights.db.Flight
import com.abaffy.dailyflights.extension.gone
import com.abaffy.dailyflights.extension.visible
import com.abaffy.dailyflights.ui.detail.FlightDetailActivity
import com.abaffy.dailyflights.util.Resource
import dagger.android.support.DaggerFragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_flight_list.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import org.reactivestreams.Subscription


class FlightListFragment : DaggerFragment() {

    companion object {
        fun newInstance() = FlightListFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: FlightListViewModel

    private var flightAdapter: FlightAdapter? = null

    private var flightsSubscription: Disposable? = null

    private val onFlightClickListener by lazy {
        object : FlightAdapter.OnFlightClickListener {
            override fun onFlightClicked(flightId: String) {
                context?.let {
                    flightAdapter?.setListener(null)
                    it.startActivity(FlightDetailActivity.createIntent(flightId, it))
                }
            }
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FlightListViewModel::class.java)

        flightAdapter = FlightAdapter()

        recycler_flights.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = flightAdapter
        }

        swipe_refresh.setOnRefreshListener {
            loadFlights()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_flight_list, container, false)
    }

    override fun onStart() {
        super.onStart()

        flightAdapter?.setListener(onFlightClickListener)

        loadFlights()
    }

    private fun loadFlights() {
        if (flightsSubscription?.isDisposed == false) {
            flightsSubscription?.dispose()
        }

        flightsSubscription = viewModel.getFlights()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onNext = this::onNext)
    }

    private fun onNext(result: Resource<List<Flight>>) {
        when (result) {
            is Resource.Success -> {
                flightAdapter?.setItems(result.data)
                empty_view.visible { flightAdapter?.itemCount == 0 }
                recycler_flights.visible { flightAdapter?.itemCount != 0 }
                progress_bar.gone()
            }
            is Resource.Failure -> {
                flightAdapter?.setItems(emptyList())
                empty_view.visible()
                recycler_flights.gone()
                progress_bar.gone()
                Snackbar.make(coordinator, R.string.error_flights, Snackbar.LENGTH_SHORT).show()
            }
            is Resource.Loading -> {
                flightAdapter?.setItems(emptyList())
                empty_view.gone()
                recycler_flights.gone()
                progress_bar.visible()
            }
        }

        swipe_refresh.isRefreshing = false

    }

    override fun onStop() {
        super.onStop()
        flightsSubscription?.dispose()
    }

}
