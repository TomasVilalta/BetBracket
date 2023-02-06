package com.example.betbracket.players

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.betbracket.R
import com.example.betbracket.databinding.FragmentPlayersBinding
import com.example.betbracket.players.adapter.PlayerAdapter

class PlayersFragment : Fragment() {
    private var _binding: FragmentPlayersBinding? = null
    private val binding get() = _binding!!
    private val playerMutableList: MutableList<Player> = PlayerProvider.playerList.toMutableList()
    private lateinit var adapter: PlayerAdapter
    private val lLayoutManager= LinearLayoutManager(this.context)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlayersBinding.inflate(inflater, container, false)
        initRecyclerView()
        updatePlayerCount()
        binding.addFab.setOnClickListener { createPlayer() }
        return binding.root
    }

    private fun initRecyclerView() {
        adapter =
            PlayerAdapter(
                playerList = playerMutableList,
                onClickDelete = { playerPos -> onDeleteItem(playerPos) })
        binding.playerList.layoutManager = lLayoutManager
        binding.playerList.adapter = adapter
    }

    private fun createPlayer() {
        var newPlayer = Player("Paco", 100, 5)
        playerMutableList.add(newPlayer)
        adapter.notifyItemInserted(playerMutableList.size - 1)
        lLayoutManager.scrollToPosition(playerMutableList.size-1)
        updatePlayerCount()
        if (playerMutableList.size > 0) {
            hideEmptyListUI()
        }
    }

    private fun onDeleteItem(playerPos: Int) {
        playerMutableList.removeAt(playerPos)
        adapter.notifyItemRemoved(playerPos)
        updatePlayerCount()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    //    I DONT KNOW WHAT TO DO WITH THIS

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        val menuHost: MenuHost = requireActivity()
//        menuHost.addMenuProvider(object : MenuProvider {
//            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
//                menuInflater.inflate(R.menu.bottom_nav_menu, menu)
//            }
//            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
//                return false
//            }
//        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
//    }


}
