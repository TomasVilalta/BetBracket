package com.example.betbracket.players

import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.betbracket.R
import com.example.betbracket.databinding.FragmentPlayersBinding
import com.example.betbracket.players.adapter.PlayerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class PlayersFragment : Fragment() {
    private var _binding: FragmentPlayersBinding? = null
    private val binding get() = _binding!!
    private val playerMutableList: MutableList<Player> = PlayerProvider.playerList.toMutableList()
    private lateinit var adapter: PlayerAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlayersBinding.inflate(inflater, container, false)

        checkBottomNav()
        initRecyclerView()
        binding.addFab.setOnClickListener{ view: View ->
                    view.findNavController().navigate(R.id.action_playersFragment_to_playerFormFragment)
                    createPlayer()
        }



        updatePlayerCount()

        return binding.root
    }

    private fun checkBottomNav() {
//        val bottomNav = (activity as AppCompatActivity).findViewById<BottomNavigationView>(R.id.bottom_navigation)
//        if(bottomNav.visibility == View.GONE){
//            val bottomNavAnimation : Animation = AnimationUtils.loadAnimation(requireContext(),
//                R.anim.slide_up_in)
//            bottomNav.startAnimation(bottomNavAnimation)
//            bottomNav.visibility = View.VISIBLE
//        }
    }

    private fun initRecyclerView() {
        adapter =
            PlayerAdapter(
                playerList = playerMutableList,
                onClickDelete = { playerPos -> onDeleteItem(playerPos) })
        binding.playerList.layoutManager = LinearLayoutManager(this.context)
        binding.playerList.adapter = adapter
    }

    private fun createPlayer() {



        val newPlayer = Player("Paco", 100)
        playerMutableList.add(newPlayer)
        adapter.notifyItemInserted(playerMutableList.size - 1)
        binding.playerList.layoutManager?.scrollToPosition(playerMutableList.size-1)
        updatePlayerCount()
        if (playerMutableList.size > 0) {
            hideEmptyListUI()
        }
    }

    private fun onDeleteItem(playerPos: Int) {
        MaterialAlertDialogBuilder(
            this.requireContext(),
            R.style.AlertDialog_BetBracket
        )
            .setMessage("¿Quieres eliminar a ${playerMutableList[playerPos].name}?")
            .setPositiveButton("Si") { _, _ ->
                playerMutableList.removeAt(playerPos)
                adapter.notifyItemRemoved(playerPos)
                updatePlayerCount()
            }
            .setNegativeButton("No") { _, _ ->
            }.show()

    }

    private fun updatePlayerCount() {
        binding.playerCountText.text = getString(R.string.jugadoresCount, playerMutableList.size)
        if (playerMutableList.size == 0) {
            showEmptyListUI()
        }
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



    //    I DONT KNOW WHAT TO DO WITH THIS

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
