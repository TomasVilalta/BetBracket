package com.example.betbracket.players.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.betbracket.R
import com.example.betbracket.database.models.Player

class PlayerAdapter(
    var playerList: List<Player>,
    private val onClickDelete: (Int) -> Unit,
    private val onClickEdit: (Int) -> Unit,

    ) : RecyclerView.Adapter<PlayerViewHolder>() {
    override fun getItemCount(): Int = playerList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PlayerViewHolder(layoutInflater.inflate(R.layout.view_player, parent, false))
    }


    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val item = playerList[position]
        holder.render(item, onClickDelete, onClickEdit)
    }
}