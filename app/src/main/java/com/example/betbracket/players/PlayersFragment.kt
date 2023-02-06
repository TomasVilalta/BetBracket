package com.example.betbracket.players

import android.os.Bundle
import android.view.*
import android.widget.Adapter
import android.widget.Toast
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlayersBinding.inflate(inflater, container, false)
        initRecyclerView()
        updatePlayerCount()
        return binding.root
    }

    private fun initRecyclerView() {
        adapter =
            PlayerAdapter(
                playerList = playerMutableList,
                onClickDelete = { playerPos -> onDeleteItem(playerPos) })
        binding.playerList.layoutManager = LinearLayoutManager(this.context)
        binding.playerList.adapter = adapter
    }

    private fun onDeleteItem(playerPos: Int) {
        Toast.makeText(this.context, "$playerPos", Toast.LENGTH_SHORT).show()
        playerMutableList.removeAt(playerPos)
        adapter.notifyItemRemoved(playerPos)
        updatePlayerCount()
    }

    private fun updatePlayerCount() {
        binding.playerCountText.text = getString(R.string.jugadoresCount,playerMutableList.size)
        if (playerMutableList.size == 0){
            binding.playerCountText.visibility = View.INVISIBLE
            binding.lineView.visibility = View.INVISIBLE
            binding.noPlayersText.visibility = View.VISIBLE
            binding.noPlayersTextBody.visibility = View.VISIBLE
            binding.sadfaceImageView.visibility = View.VISIBLE
        }
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
