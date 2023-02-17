package com.example.betbracket.events.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.betbracket.R
import com.example.betbracket.events.models.Event

class EventsAdapter(val eventsList: List<Event>): RecyclerView.Adapter<EventsViewHolder>() {

    override fun getItemCount(): Int = eventsList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return EventsViewHolder(layoutInflater.inflate(R.layout.view_event,parent, false))
    }



    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        val item = eventsList[position]
        return holder.render(item)
    }
}