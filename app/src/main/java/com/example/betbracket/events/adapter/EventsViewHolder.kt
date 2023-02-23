package com.example.betbracket.events.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.betbracket.databinding.ViewEventBinding
import com.example.betbracket.database.models.Event

class EventsViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = ViewEventBinding.bind(view)

    fun render(event: Event, onClickDelete: (Int) -> Unit, onClickSelect: (Int) -> Unit) {
        binding.eventTitleText.text = event.title
        binding.player1Name.text = event.teamA.name
        binding.player2Name.text = event.teamB.name
        Glide.with(binding.player1Image.context).load(event.teamA.image).into(binding.player1Image)
        Glide.with(binding.player1Image.context).load(event.teamB.image).into(binding.player2Image)
        binding.deleteEvent.setOnClickListener{
            onClickDelete(adapterPosition)
        }
        binding.eventCard.setOnClickListener{
            onClickSelect((adapterPosition))
        }


    }

}
