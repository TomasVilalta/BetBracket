package com.example.betbracket.events.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.betbracket.R
import com.example.betbracket.database.entities.Event
import com.example.betbracket.database.relations.EventWithPlayers

class EventsAdapter(
    private val onClickDelete: (Int) -> Unit,
    private val onClickSelect: (String) -> Unit,
) : RecyclerView.Adapter<EventsViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<EventWithPlayers>() {
        override fun areItemsTheSame(oldItem: EventWithPlayers, newItem: EventWithPlayers): Boolean {
            return oldItem.event.title == newItem.event.title
        }
        override fun areContentsTheSame(oldItem: EventWithPlayers, newItem: EventWithPlayers): Boolean {
            return oldItem.event == newItem.event
        }
    }

    val differ = AsyncListDiffer(this,differCallback)

    override fun getItemCount(): Int = differ.currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return EventsViewHolder(layoutInflater.inflate(R.layout.view_event, parent, false))
    }


    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        val item = differ.currentList[position]
        return holder.render(item, onClickDelete, onClickSelect)
    }


}