package com.example.betbracket.players

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.betbracket.R
import com.example.betbracket.abstractFragments.MainScreenAbstractFragment
import com.example.betbracket.database.BetDatabase
import com.example.betbracket.database.entities.Player
import com.example.betbracket.databinding.FragmentPlayersBinding
import com.example.betbracket.players.adapter.PlayerAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class PlayersFragment : MainScreenAbstractFragment() {

    private lateinit var playerViewModel: PlayerViewModel
    private var _binding: FragmentPlayersBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PlayerAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlayersBinding.inflate(inflater, container, false)
        Log.i("VIEWMODEL", "ASSIGNED TO PLAYER FRAG")
        val playersRepository = PlayersRepository(BetDatabase.getInstance(requireContext()))
        val viewModelProviderFactory = PlayerViewModelProviderFactory(playersRepository)
        playerViewModel =
            ViewModelProvider(this, viewModelProviderFactory)[PlayerViewModel::class.java]
        Log.i("diomio", "playerViewmodel created")
        playerViewModel.onCreatePlayer(
            Player(
                "Juan",
                100.0,
                "https://miro.medium.com/max/720/1*W35QUSvGpcLuxPo3SRTH4w.png",
                -14.5
            )
        )

        playerViewModel.onCreatePlayer(
            Player(
                "Pepe",
                74.0,
                "https://miro.medium.com/max/720/1*W35QUSvGpcLuxPo3SRTH4w.png",
                21.7
            )
        )

        initRecyclerView()

        // Observers
        playerViewModel.playerCount.observe(viewLifecycleOwner) { newPlayerCount ->
            binding.playerCountText.text = getString(R.string.jugadoresCount, newPlayerCount)
            if (newPlayerCount == 0) {
                showEmptyListUI()
            } else {
                hideEmptyListUI()
            }
        }

        playerViewModel.getPlayers().observe(viewLifecycleOwner) { playerList ->
            adapter.differ.submitList(playerList)
        }

        //  Click listeners
        binding.addFab.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(PlayersFragmentDirections.actionPlayersFragmentToPlayerFormFragment(null))
        }

        return binding.root
    }


    private fun initRecyclerView() {
        adapter =
            PlayerAdapter({ playerPos -> onDeleteItem(playerPos) },
                { playerPos -> onEditItem(playerPos) })
        binding.playerList.layoutManager = LinearLayoutManager(activity)
        binding.playerList.adapter = adapter

    }

    private fun onEditItem(playerPos: Int) {
        val playerName = adapter.differ.currentList[playerPos].name
        this.findNavController()
            .navigate(PlayersFragmentDirections.actionPlayersFragmentToPlayerFormFragment(playerName))
    }

    private fun onDeleteItem(playerPos: Int) {
        val player = adapter.differ.currentList[playerPos]
        MaterialAlertDialogBuilder(
            this.requireContext(),
            R.style.AlertDialog_BetBracket
        )
            .setMessage("¿Quieres eliminar a ${player.name}?")
            .setPositiveButton("Si") { _, _ ->
                playerViewModel.onDelete(player)
            }
            .setNegativeButton("No") { _, _ ->
            }.show()
    }

    private fun hideEmptyListUI() {
        binding.noPlayersText.visibility = View.INVISIBLE
        binding.noPlayersTextBody.visibility = View.INVISIBLE
        binding.sadfaceImageView.visibility = View.INVISIBLE
        binding.playerCountText.visibility = View.VISIBLE
        binding.lineView.visibility = View.VISIBLE
    }

    private fun showEmptyListUI() {
        binding.playerCountText.visibility = View.INVISIBLE
        binding.lineView.visibility = View.INVISIBLE
        binding.noPlayersText.visibility = View.VISIBLE
        binding.noPlayersTextBody.visibility = View.VISIBLE
        binding.sadfaceImageView.visibility = View.VISIBLE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.top_bar_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }


}


