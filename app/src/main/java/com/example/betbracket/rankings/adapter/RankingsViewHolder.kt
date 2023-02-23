package com.example.betbracket.rankings.adapter

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.betbracket.R
import com.example.betbracket.databinding.ViewRankingBinding
import com.example.betbracket.database.models.Player

class RankingsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ViewRankingBinding.bind(view)
    private val viewContext = view.context


    private fun roundOff(num: Double):String = (Math.round(num * 100.0) / 100.0).toString()
    fun render(player: Player) {
        binding.apply {
            playerName.text = player.name
            balanceText.text = viewContext.getString(R.string.playerBalance, roundOff(player.balance))
            Glide.with(binding.playerImage.context).load(player.image).into(binding.playerImage)

            if (player.lastWagerResult < 0) {
                lastWagerText.setTextColor(
                    ContextCompat.getColor(
                        viewContext,
                        R.color.error
                    )
                )
                lastWagerText.text = player.lastWagerResult.toString()
            } else if (player.lastWagerResult > 0) {
                lastWagerText.setTextColor(
                    ContextCompat.getColor(
                        viewContext,
                        R.color.successColor
                    )
                )
                lastWagerText.text =
                    viewContext.getString(R.string.positiveIntFormat, roundOff(player.lastWagerResult))
            } else {
                lastWagerText.setTextColor(
                    ContextCompat.getColor(
                        viewContext,
                        R.color.onSecondaryContainer
                    )
                )
                lastWagerText.text =
                    viewContext.getString(R.string.positiveIntFormat, roundOff(player.lastWagerResult))
            }


        }

    }
}