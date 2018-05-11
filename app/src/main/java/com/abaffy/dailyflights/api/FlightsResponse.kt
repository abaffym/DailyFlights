package com.abaffy.dailyflights.api

import com.abaffy.dailyflights.db.Flight

data class FlightsResponse(
        val data: List<Flight>
)