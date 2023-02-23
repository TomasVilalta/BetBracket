package com.example.betbracket.events.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.betbracket.R
import com.example.betbracket.database.models.Event

class EventsAdapter(
    var eventsList: List<Event>,
    private val onClickDelete: (Int) -> Unit,
    private val onClickSelect: (Int) -> Unit
) : RecyclerView.Adapter<EventsViewHolder>() {

    override fun getItemCount(): Int = eventsList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return EventsViewHolder(layoutInflater.inflate(R.layout.view_event, parent, false))
    }


    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        val item = eventsList[position]
        return holder.render(item, onClickDelete, onClickSelect)
    }
}