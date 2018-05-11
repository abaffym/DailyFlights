package com.abaffy.dailyflights.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [
    Flight::class
], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun flightDao(): FlightDao

}