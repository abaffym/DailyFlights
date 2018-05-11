package com.abaffy.dailyflights.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.abaffy.dailyflights.api.Webservice
import com.abaffy.dailyflights.util.DateUtils

@Entity(tableName = "flight")
data class Flight(
        @PrimaryKey val id: String,
        val mapIdto: String,
        val cityFrom: String,
        val cityTo: String,
        val flyFrom: String,
        val flyTo: String,
        val countryFrom: String,
        val countryFromCode: String,
        val countryTo: String,
        val countryToCode: String,
        val dTime: Long,
        val aTime: Long,
        val flyDuration: String,
        val price: Int,
        val apiDate: String
) {

    val departureTime: String
        get() = DateUtils.getSimpleTime(dTime)

    val arrivalTime: String
        get() = DateUtils.getSimpleTime(aTime)

    val departureDate: String
        get() = DateUtils.getSimpleDate(dTime)

    val arrivalDate: String
        get() = DateUtils.getSimpleDate(aTime)

    val pictureUrl: String
        get() = Webservice.PHOTOS_URL + mapIdto + ".jpg"

}