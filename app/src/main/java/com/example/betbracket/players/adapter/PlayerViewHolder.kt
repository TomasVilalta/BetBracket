package com.example.betbracket.players.adapter

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.betbracket.databinding.ViewPlayerBinding
import com.example.betbracket.database.models.Player

class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ViewPlayerBinding.bind(view)

    fun render(player: Player, onClickDelete: (Int) -> Unit, onClickEdit: (Int) -> Unit) {
        binding.playerName.text = player.name
        Glide.with(binding.playerImage.context).load(player.image).into(binding.playerImage)
        binding.playerDelete.setOnClickListener {
            Log.i("onDelete", "playerPos sent by viewHolder: $adapterPosition")
            onClickDelete(adapterPosition)
        }
        binding.playerEdit.setOnClickListener{
            onClickEdit(adapterPosition)
        }
    }
}