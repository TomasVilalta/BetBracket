package com.example.betbracket.rankings.adapter

import android.icu.text.NumberFormat
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.betbracket.R
import com.example.betbracket.databinding.ViewRankingBinding
import com.example.betbracket.players.Player

class RankingsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ViewRankingBinding.bind(view)
    private val viewContext = view.context


    fun render(player: Player) {
        binding.playerName.text = player.name
        binding.balanceText.text = viewContext.getString(R.string.playerBalance, player.balance)
        if (player.lastWagerResult < 0) {
            binding.lastWagerText.setTextColor(ContextCompat.getColor(viewContext, R.color.error))
            binding.lastWagerText.text = player.lastWagerResult.toString()
        } else if (player.lastWagerResult > 0) {
            binding.lastWagerText.setTextColor(
                ContextCompat.getColor(
                    viewContext,
                    R.color.successColor
                )
            )
            binding.lastWagerText.text =
                viewContext.getString(R.string.positiveIntFormat, player.lastWagerResult)
        } else {
            binding.lastWagerText.setTextColor(
                ContextCompat.getColor(
                    viewContext,
                    R.color.onSecondaryContainer
                )
            )
            binding.lastWagerText.text =
                viewContext.getString(R.string.positiveIntFormat, player.lastWagerResult)
        }


    }

}
