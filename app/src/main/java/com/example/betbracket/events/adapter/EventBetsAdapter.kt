package com.example.betbracket.events.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.betbracket.R
import com.example.betbracket.database.entities.Bet
import com.example.betbracket.databinding.ViewPlayerBetBinding

class EventBetsAdapter(): RecyclerView.Adapter<EventBetsAdapter.EventBetsViewHolder>() {

    inner class EventBetsViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ViewPlayerBetBinding.bind(view)
        fun render(item: Bet) {
            binding.playerName.text = item.betPlayerName
            binding.betAmountText.text = binding.playerCard.context.getString(R.string.playerBalance, (Math.round(item.amount * 100.0) / 100.0).toString())
            binding.playerPredictionText.text = item.prediction
        }

    }

    private val differCallback = object : DiffUtil.ItemCallback<Bet>(){
        override fun areItemsTheSame(oldItem: Bet, newItem: Bet): Boolean {
            return oldItem.betPlayerName == newItem.betPlayerName
        }

        override fun areContentsTheSame(oldItem: Bet, newItem: Bet): Boolean {
            return  oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallback)


    override fun getItemCount(): Int = differ.currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventBetsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return EventBetsViewHolder(layoutInflater.inflate(R.layout.view_player_bet, parent, false))
    }

    override fun onBindViewHolder(holder: EventBetsViewHolder, position: Int) {
        val item = differ.currentList[position]
        return holder.render(item)
    }

}