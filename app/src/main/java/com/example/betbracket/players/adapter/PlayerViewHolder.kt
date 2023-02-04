package com.example.betbracket.players.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.betbracket.databinding.ViewPlayerBinding
import com.example.betbracket.players.Player

class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ViewPlayerBinding.bind(view)

    fun render(player: Player){
        binding.playerName.text = player.name
    }
}