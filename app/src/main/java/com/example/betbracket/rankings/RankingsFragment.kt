package com.example.betbracket.rankings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.betbracket.MainScreenAbstractFragment
import com.example.betbracket.databinding.FragmentRankingsBinding
import com.example.betbracket.players.Player
import com.example.betbracket.players.PlayerProvider
import com.example.betbracket.rankings.adapter.RankingsAdapter


class RankingsFragment : MainScreenAbstractFragment() {

    val rankingList: List<Player> = PlayerProvider.getPlayers()
    lateinit var adapter: RankingsAdapter
    private var _binding: FragmentRankingsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRankingsBinding.inflate(inflater, container, false)
        initRecyclerView()
        return binding.root
    }

    private fun initRecyclerView() {
        adapter = RankingsAdapter(rankingList.sortedByDescending { it.balance })
        binding.rankingsList.layoutManager = LinearLayoutManager(activity)
        binding.rankingsList.adapter = adapter
    }

}