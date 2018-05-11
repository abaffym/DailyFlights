package com.abaffy.dailyflights.ui.list


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abaffy.dailyflights.R
import com.abaffy.dailyflights.db.Flight
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_flight.view.*

class FlightAdapter : RecyclerView.Adapter<FlightAdapter.ViewHolder>() {

    interface OnFlightClickListener {
        fun onFlightClicked(flightId: String)
    }

    private val flights: MutableList<Flight> = mutableListOf()

    private var listener: OnFlightClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_flight, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val flight = flights[position]
        holder.view.tv_flight_to.text = flight.cityTo
        holder.view.tv_flight_price.text = String.format(holder.view.context.getString(R.string.format_price_from, flight.price))

        Picasso.get()
                .load(flight.pictureUrl)
                .placeholder(R.color.primary)
                .into(holder.view.iv_flight)

    }

    override fun getItemCount(): Int = flights.size

    fun setItems(items: List<Flight>) {
        flights.clear()
        flights.addAll(items)
        notifyDataSetChanged()
    }

    fun setListener(listener: OnFlightClickListener?) {
        this.listener = listener
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        init {
            view.setOnClickListener {
                listener?.onFlightClicked(flights[adapterPosition].id)
            }
        }

    }

}
