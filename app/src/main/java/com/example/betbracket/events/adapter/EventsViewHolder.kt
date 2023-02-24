package com.example.betbracket.events.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.betbracket.databinding.ViewEventBinding
import com.example.betbracket.database.entities.Event
import com.example.betbracket.events.EventViewModel

class EventsViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = ViewEventBinding.bind(view)

    fun render(
        event: Event,
        onClickDelete: (Int) -> Unit,
        onClickSelect: (Int) -> Unit,
        viewModel: EventViewModel
    ) {
        binding.eventTitleText.text = event.title
        binding.player1Name.text = event.player1Name
        binding.player2Name.text = event.player2Name
        Glide.with(binding.player1Image.context).load(viewModel.getPlayerByName(event.player1Name).image).into(binding.player1Image)
        Glide.with(binding.player1Image.context).load(viewModel.getPlayerByName(event.player2Name).image).into(binding.player2Image)
        binding.deleteEvent.setOnClickListener{
            onClickDelete(adapterPosition)
        }
        binding.eventCard.setOnClickListener{
            onClickSelect((adapterPosition))
        }


    }

}
