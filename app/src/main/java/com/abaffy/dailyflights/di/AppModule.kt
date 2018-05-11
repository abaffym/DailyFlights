package com.abaffy.dailyflights.di

import android.arch.persistence.room.Room
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.abaffy.dailyflights.App
import com.abaffy.dailyflights.api.FlightDeserializer
import com.abaffy.dailyflights.api.Webservice
import com.abaffy.dailyflights.db.AppDatabase
import com.abaffy.dailyflights.db.Flight
import com.abaffy.dailyflights.db.FlightDao
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    fun provideAppContext(app: App): Context = app.applicationContext

    @Provides
    @Singleton
    fun provideAppPreferences(application: App): SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(application)


    @Provides
    @Singleton
    fun provideGson(): Gson =
            GsonBuilder()
                    .registerTypeAdapter(Flight::class.java, FlightDeserializer())
                    .create()

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): Interceptor =
            HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BASIC)

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: Interceptor): OkHttpClient =
            OkHttpClient.Builder()
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(loggingInterceptor)
                    .build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit =
            Retrofit.Builder()
                    .baseUrl("https://api.skypicker.com/")
                    .client(client)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()

    @Provides
    @Singleton
    fun provideWebservice(retrofit: Retrofit): Webservice =
            retrofit.create(Webservice::class.java)

    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, "dailyflights.db")
                    .build()

    @Provides
    fun provideCharacterDao(db: AppDatabase): FlightDao = db.flightDao()

}