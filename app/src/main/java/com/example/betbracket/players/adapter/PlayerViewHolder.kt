package com.example.betbracket.players.adapter

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.betbracket.databinding.ViewPlayerBinding
import com.example.betbracket.players.Player

class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ViewPlayerBinding.bind(view)

    fun render(player: Player) {
        binding.playerName.text = player.name
        binding.playerCard.setOnClickListener {
            Toast.makeText(binding.playerCard.context," ${player.name}", Toast.LENGTH_SHORT)
                .show()
        }
        binding.playerDelete.setOnClickListener {
            Toast.makeText(binding.playerCard.context,"chaupe", Toast.LENGTH_SHORT)
                .show()
        }
    }
}