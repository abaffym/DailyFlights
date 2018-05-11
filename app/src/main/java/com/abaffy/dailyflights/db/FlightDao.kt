package com.abaffy.dailyflights.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
abstract class FlightDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(flight: Flight)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(flight: List<Flight>)

    @Query("SELECT * FROM FLIGHT WHERE id = :id")
    abstract fun getById(id: String): Flowable<Flight>

    @Query("SELECT * FROM FLIGHT")
    abstract fun getAll(): Flowable<List<Flight>>

    @Query("SELECT * FROM FLIGHT WHERE apiDate = :apiDate")
    abstract fun getForDate(apiDate: String): Single<List<Flight>>

    @Query("DELETE FROM FLIGHT")
    abstract fun deleteAll()

}