package com.abaffy.dailyflights.util

import org.joda.time.DateTime
import java.text.SimpleDateFormat

object DateUtils {

    private val simpleTimeFormat = SimpleDateFormat("HH:mm")

    private val simpleDateFormat = SimpleDateFormat("dd. MMM")

    private val apiDateFormat = SimpleDateFormat("dd/MM/yyyy")

    fun getSimpleTime(time: Long): String = simpleTimeFormat.format(time)

    fun getSimpleDate(time: Long): String = simpleDateFormat.format(time)

    fun getApiDate(): String = apiDateFormat.format(DateTime().plusDays(1).withTimeAtStartOfDay().millis)

}