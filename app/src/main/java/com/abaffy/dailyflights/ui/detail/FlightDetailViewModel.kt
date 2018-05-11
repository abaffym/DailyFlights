package com.abaffy.dailyflights.ui.detail

import android.arch.lifecycle.ViewModel
import com.abaffy.dailyflights.db.FlightDao
import javax.inject.Inject


class FlightDetailViewModel @Inject constructor(
        private val flightDao: FlightDao
) : ViewModel() {

    fun getFlight(id: String) = flightDao.getById(id)

}