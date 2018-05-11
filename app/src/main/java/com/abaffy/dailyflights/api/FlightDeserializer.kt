package com.abaffy.dailyflights.api

import com.abaffy.dailyflights.db.Flight
import com.abaffy.dailyflights.util.DateUtils
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class FlightDeserializer : JsonDeserializer<Flight> {

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Flight {
        val flightJson = json.asJsonObject
        return Flight(
                id = flightJson.get("id").asString,
                mapIdto = flightJson.get("mapIdto").asString,
                cityTo = flightJson.get("cityTo").asString,
                cityFrom = flightJson.get("cityFrom").asString,
                countryTo = flightJson.get("countryTo").asJsonObject.get("name").asString,
                countryToCode = flightJson.get("countryTo").asJsonObject.get("code").asString,
                countryFrom = flightJson.get("countryFrom").asJsonObject.get("name").asString,
                countryFromCode = flightJson.get("countryFrom").asJsonObject.get("code").asString,
                flyTo = flightJson.get("flyTo").asString,
                flyFrom = flightJson.get("flyFrom").asString,
                dTime = flightJson.get("dTime").asLong * 1000,
                aTime = flightJson.get("aTime").asLong * 1000,
                flyDuration = flightJson.get("fly_duration").asString,
                price = flightJson.get("price").asInt,
                apiDate = DateUtils.getApiDate()
        )
    }

}