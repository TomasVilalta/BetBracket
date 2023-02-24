package com.example.betbracket.events.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.betbracket.R
import com.example.betbracket.database.entities.Event
import com.example.betbracket.events.EventViewModel

class EventsAdapter(
    private val eventViewModel: EventViewModel,
    private val onClickDelete: (Int) -> Unit,
    private val onClickSelect: (Int) -> Unit,
) : RecyclerView.Adapter<EventsViewHolder>() {


    private val differCallback = object : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem == newItem
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
        return holder.render(item, onClickDelete, onClickSelect, eventViewModel)
    }


}