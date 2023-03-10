package com.example.betbracket.players.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.betbracket.R
import com.example.betbracket.database.entities.Player

class PlayerAdapter(
    private val onClickDelete: (Int) -> Unit,
    private val onClickEdit: (Int) -> Unit,

    ) : RecyclerView.Adapter<PlayerViewHolder>() {


    private val differCallBack = object : DiffUtil.ItemCallback<Player>(){
        override fun areItemsTheSame(oldItem: Player, newItem: Player): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Player, newItem: Player): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)
    override fun getItemCount(): Int = differ.currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PlayerViewHolder(layoutInflater.inflate(R.layout.view_player, parent, false))
    }


    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.render(item, onClickDelete, onClickEdit)
    }
}