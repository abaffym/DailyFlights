package com.abaffy.dailyflights.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.abaffy.dailyflights.R
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_flight_detail.*

class FlightDetailActivity : DaggerAppCompatActivity() {

    companion object {

        const val EXTRA_FLIGHT_ID = "flight_id"

        fun createIntent(flightId: String, context: Context) =
                Intent(context, FlightDetailActivity::class.java).apply {
                    putExtra(EXTRA_FLIGHT_ID, flightId)
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flight_detail)

        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            elevation = 0f
            setDisplayHomeAsUpEnabled(true)
        }

        if (savedInstanceState == null) {

            val flightId = intent.extras.getString(EXTRA_FLIGHT_ID)

            supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, FlightDetailFragment.newInstance(flightId))
                    .commit()
        }
    }
}
