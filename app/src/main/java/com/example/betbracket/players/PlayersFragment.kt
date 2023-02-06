package com.example.betbracket.players

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.betbracket.databinding.FragmentPlayersBinding
import com.example.betbracket.players.adapter.PlayerAdapter

class PlayersFragment : Fragment() {
    private var _binding: FragmentPlayersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlayersBinding.inflate(inflater, container, false)

        binding.playerList.layoutManager = LinearLayoutManager(this.context)
        binding.playerList.adapter = PlayerAdapter(PlayerProvider.playerList)
        return binding.root
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
