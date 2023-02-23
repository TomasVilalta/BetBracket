package com.example.betbracket.rankings.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.betbracket.R
import com.example.betbracket.database.models.Player

class RankingsAdapter(var rankingsList: List<Player>) : RecyclerView.Adapter<RankingsViewHolder>() {
    override fun getItemCount(): Int = rankingsList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RankingsViewHolder(layoutInflater.inflate(R.layout.view_ranking, parent, false))
    }


    override fun onBindViewHolder(holder: RankingsViewHolder, position: Int) {
        val item = rankingsList[position]
        holder.render(item)
    }


}