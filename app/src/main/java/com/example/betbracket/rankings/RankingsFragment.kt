package com.example.betbracket.rankings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.betbracket.abstractFragments.MainScreenAbstractFragment
import com.example.betbracket.database.BetDatabase
import com.example.betbracket.database.entities.Player
import com.example.betbracket.databinding.FragmentRankingsBinding
import com.example.betbracket.players.PlayersRepository
import com.example.betbracket.rankings.adapter.RankingsAdapter


class RankingsFragment : MainScreenAbstractFragment() {

    lateinit var adapter: RankingsAdapter
    private var _binding: FragmentRankingsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRankingsBinding.inflate(inflater, container, false)
        PlayersRepository(BetDatabase.getInstance(requireContext())).getPlayers().observe(viewLifecycleOwner, Observer {
            initRecyclerView(it)
            if (it.isEmpty()){
                binding.noPlayersText.visibility = View.VISIBLE
                binding.noPlayersTextBody.visibility = View.VISIBLE
                binding.sadfaceImageView.visibility = View.VISIBLE
            }
        })

        return binding.root
    }

    private fun initRecyclerView(players: List<Player>) {
        adapter = RankingsAdapter(players.sortedByDescending { it.balance })
        binding.rankingsList.layoutManager = LinearLayoutManager(activity)
        binding.rankingsList.adapter = adapter
    }

}