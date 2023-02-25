package com.example.betbracket.events.adapter

import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.betbracket.databinding.ViewEventBinding
import com.example.betbracket.database.entities.Event
import com.example.betbracket.database.entities.Player
import com.example.betbracket.events.EventViewModel

class EventsViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = ViewEventBinding.bind(view)
    private var playerImage: String? = null
    fun render(
        event: Event,
        onClickDelete: (Int) -> Unit,
        onClickSelect: (Int) -> Unit,
        viewModel: EventViewModel
    ) {

        binding.eventTitleText.text = event.title
        binding.player1Name.text = event.player1Name
        binding.player2Name.text = event.player2Name

        viewModel.getPlayerByName(event.player1Name)
        playerImage = viewModel.player.value?.image
        Log.i("diomio","player1 image is $playerImage")
        Glide.with(binding.player1Image.context).load(playerImage).into(binding.player1Image)

        viewModel.getPlayerByName(event.player2Name)
        playerImage = viewModel.player.value?.image
        Log.i("diomio","player2 image is $playerImage")
        Glide.with(binding.player1Image.context).load(playerImage).into(binding.player2Image)
            binding.deleteEvent.setOnClickListener{
            onClickDelete(adapterPosition)
        }
        binding.eventCard.setOnClickListener{
            onClickSelect((adapterPosition))
        }


    }

}
