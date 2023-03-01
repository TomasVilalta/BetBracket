package com.example.betbracket.events.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.betbracket.database.relations.EventWithPlayers
import com.example.betbracket.databinding.ViewEventBinding

class EventsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ViewEventBinding.bind(view)
    private var playerImage: String? = null
    fun render(
        item: EventWithPlayers,
        onClickDelete: (Int) -> Unit,
        onClickSelect: (String) -> Unit,
    ) {
        binding.eventTitleText.text = item.event.title
        binding.player1Name.text = item.event.player1Name
        binding.player2Name.text = item.event.player2Name
        Glide.with(binding.deleteEvent.context).load(item.player1.image).into(binding.player1Image)
        Glide.with(binding.deleteEvent.context).load(item.player2.image).into(binding.player2Image)
        binding.deleteEvent.setOnClickListener {
            onClickDelete(adapterPosition)
        }
        binding.eventCard.setOnClickListener {
            onClickSelect((item.event.title))
        }

    }

}
