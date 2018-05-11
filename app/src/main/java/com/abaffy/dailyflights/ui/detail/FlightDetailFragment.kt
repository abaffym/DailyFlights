package com.abaffy.dailyflights.ui.detail

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.abaffy.dailyflights.R
import com.abaffy.dailyflights.db.Flight
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_flight_detail.*
import javax.inject.Inject

class FlightDetailFragment : DaggerFragment() {

    companion object {

        const val ARG_FLIGHT_ID = "flight_id"

        fun newInstance(id: String) = FlightDetailFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_FLIGHT_ID, id)
            }
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: FlightDetailViewModel

    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_flight_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FlightDetailViewModel::class.java)

        val flightId = arguments?.getString(ARG_FLIGHT_ID)
                ?: throw IllegalArgumentException("No flight provided.")

        compositeDisposable += viewModel.getFlight(flightId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = { flight ->
                            setFlightViews(flight)
                        }
                )
    }

    private fun setFlightViews(flight: Flight) {

        tv_title_city.text = flight.cityTo
        tv_subtitile_country.text = flight.countryTo

        tv_date_departure.text = flight.departureDate
        tv_date_arrival.text = flight.arrivalDate
        tv_time_departure.text = flight.departureTime
        tv_time_arrival.text = flight.arrivalTime
        tv_city_from.text = flight.cityFrom
        tv_city_to.text = flight.cityTo
        tv_fly_from.text = flight.flyFrom
        tv_fly_to.text = flight.flyTo

        btn_book.text = getString(R.string.format_book_for, flight.price)
        btn_book.setOnClickListener {
            Toast.makeText(activity, "Action not implemented", Toast.LENGTH_SHORT).show()
        }

        Picasso.get()
                .load(flight.pictureUrl)
                .fit()
                .centerCrop()
                .into(iv_flight)
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }

}
