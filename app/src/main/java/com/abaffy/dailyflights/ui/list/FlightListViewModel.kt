package com.abaffy.dailyflights.ui.list

import android.arch.lifecycle.ViewModel
import com.abaffy.dailyflights.api.Webservice
import com.abaffy.dailyflights.db.Flight
import com.abaffy.dailyflights.db.FlightDao
import com.abaffy.dailyflights.util.DateUtils
import com.abaffy.dailyflights.util.Resource
import io.reactivex.Observable
import io.reactivex.Single
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class FlightListViewModel @Inject constructor(
        private val webservice: Webservice,
        private val flightDao: FlightDao
) : ViewModel() {

    fun getFlights(): Observable<Resource<List<Flight>>> {

        return Single.concat(
                getFlightsFromDb()
                        .map<Resource<List<Flight>>> { Resource.Success(it) },
                getFlightsFromApi()
                        .map<Resource<List<Flight>>> { Resource.Success(it) }
        ).toObservable()
                .filter { it is Resource.Success && it.data.isNotEmpty() }
                .take(1)
                .startWith(Resource.Loading())
                .onErrorReturn {
                    Resource.Failure(it)
                }

    }

    private fun getFlightsFromDb(): Single<List<Flight>> {
        return flightDao.getForDate(
                DateUtils.getApiDate()
        ).doOnSuccess {
            Timber.d("Dispatching local data")
        }.doOnError {
            Timber.e(it)
        }
    }

    private fun getFlightsFromApi(): Single<List<Flight>> {

        val tomorrowDate = DateUtils.getApiDate()

        return webservice.getTopDailyFlights(
                dateFrom = tomorrowDate,
                dateTo = tomorrowDate
        ).map {
            it.data
        }.doOnSuccess {
            flightDao.deleteAll()
            flightDao.insert(it)
            Timber.d("Dispatching remote data")
        }.doOnError {
            Timber.e(it)
        }

    }

}