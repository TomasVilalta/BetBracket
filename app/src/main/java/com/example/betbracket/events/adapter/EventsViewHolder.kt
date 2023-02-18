package com.example.betbracket.events.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.betbracket.databinding.ViewEventBinding
import com.example.betbracket.events.models.Event

class EventsViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = ViewEventBinding.bind(view)

    fun render(item: Event) {
        binding.eventTitleText.text = item.title
        binding.player1Name.text = item.teamA.name
        binding.player2Name.text = item.teamB.name
        Glide.with(binding.player1Image.context).load(item.teamA.image).into(binding.player1Image)
        Glide.with(binding.player1Image.context).load(item.teamB.image).into(binding.player2Image)

    }

}
