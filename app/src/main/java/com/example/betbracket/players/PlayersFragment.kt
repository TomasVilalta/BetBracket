package com.example.betbracket.players

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.betbracket.R
import com.example.betbracket.databinding.FragmentPlayersBinding
import com.example.betbracket.players.adapter.PlayerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class PlayersFragment : Fragment() {

    private lateinit var playerViewModel: PlayerViewModel
    private var _binding: FragmentPlayersBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PlayerAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlayersBinding.inflate(inflater, container, false)

        playerViewModel = ViewModelProvider(this)[PlayerViewModel::class.java]

        //        ViewModel Listeners

        playerViewModel.playerCount.observe(viewLifecycleOwner, Observer { newPlayerCount ->
            binding.playerCountText.text = getString(R.string.jugadoresCount, newPlayerCount)
            if (newPlayerCount == 0) {
                showEmptyListUI()
            } else {
                hideEmptyListUI()
            }

        })

        playerViewModel.playerList.observe(viewLifecycleOwner, Observer { newPlayerList ->
                adapter.playerList = newPlayerList
                adapter.notifyDataSetChanged()

        })
//        checkBottomNav()
        initRecyclerView()


        binding.addFab.setOnClickListener { view: View ->
//            view.findNavController().navigate(R.id.action_playersFragment_to_playerFormFragment)
            createPlayer()

        }




        return binding.root
    }

//    private fun checkBottomNav() {
//        val bottomNav =
//            (activity as AppCompatActivity).findViewById<BottomNavigationView>(R.id.bottom_navigation)
//        if (bottomNav.visibility == View.GONE) {
//            val bottomNavAnimation: Animation = AnimationUtils.loadAnimation(
//                requireContext(),
//                R.anim.slide_up_in
//            )
//            bottomNav.startAnimation(bottomNavAnimation)
//            bottomNav.visibility = View.VISIBLE
//        }
//    }

    private fun initRecyclerView() {
        adapter =
            PlayerAdapter{ playerPos -> onDeleteItem(playerPos) }
        binding.playerList.layoutManager = LinearLayoutManager(activity)
        binding.playerList.adapter = adapter
    }

    private fun createPlayer() {

        adapter.notifyItemInserted(playerViewModel.onCreatePlayer())
//        binding.playerList.layoutManager?.scrollToPosition(playerMutableList.size - 1)

    }

    private fun onDeleteItem(playerPos: Int) {
        MaterialAlertDialogBuilder(
            this.requireContext(),
            R.style.AlertDialog_BetBracket
        )
            .setMessage("¿Quieres eliminar a ${playerViewModel.getPlayerName(playerPos)}?")
            .setPositiveButton("Si") { _, _ ->
                playerViewModel.onDelete(playerPos)
                adapter.notifyItemRemoved(playerPos)

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


