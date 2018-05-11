package com.abaffy.dailyflights.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Webservice {

    companion object {
        const val PHOTOS_URL = "https://images.kiwi.com/photos/600/"

        const val PARAM_LOCALE_CZ = "CZ"
        const val PARAM_LIMIT = 5
        const val PARAM_SORT_POPULARITY = "popularity"
        const val PARAM_ASC = 1
        const val PARAM_DESC = 0
        const val PARAM_API_V1 = 1
        const val PARAM_API_V2 = 2
    }

    @GET("flights")
    fun getTopDailyFlights(
            @Query("flyFrom") from: String = PARAM_LOCALE_CZ,
            @Query("limit") limit: Int = PARAM_LIMIT,
            @Query("sort") sort: String = PARAM_SORT_POPULARITY,
            @Query("asc") asc: Int = PARAM_DESC,
            @Query("dateFrom") dateFrom: String,
            @Query("dateTo") dateTo: String,
            @Query("v") version: Int = PARAM_API_V2
    ): Single<FlightsResponse>

}