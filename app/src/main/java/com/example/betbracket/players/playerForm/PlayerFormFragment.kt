package com.example.betbracket.players.playerForm

import android.inputmethodservice.Keyboard
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.findNavController
import com.example.betbracket.R
import com.example.betbracket.databinding.FragmentPlayerFormBinding
import com.example.betbracket.players.Player
import com.example.betbracket.players.PlayerProvider
import com.example.betbracket.players.PlayerViewModel
import com.example.betbracket.players.adapter.PlayerAdapter
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.android.awaitFrame


class PlayerFormFragment : Fragment() {

    private var _binding: FragmentPlayerFormBinding? = null
    private val binding get() = _binding!!
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var toolBar: MaterialToolbar
    private lateinit var adapter: PlayerAdapter
    private val playerViewModel: PlayerViewModel by viewModels({requireParentFragment()})

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout to use as dialog or embedded fragment
        _binding = FragmentPlayerFormBinding.inflate(inflater, container, false)



        animateBottomNav()
        setUpToolbar()

        return binding.root
    }


    private fun setUpToolbar() {
        val toolBarAnimation: Animation = animateBottomNav()
        toolBar = (activity as AppCompatActivity).findViewById<MaterialToolbar>(R.id.topAppBar)
        toolBar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_close_24)
        toolBar.setNavigationIconTint(
            ContextCompat.getColor(
                requireContext(), R.color.onBackground
            )
        )
        toolBar.startAnimation(toolBarAnimation)
    }

    private fun animateBottomNav(): Animation {
        bottomNav =
            (activity as AppCompatActivity).findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.visibility = View.GONE
        val bottomNavAnimation: Animation = AnimationUtils.loadAnimation(
            requireContext(), R.anim.slide_down_out
        )
        bottomNav.startAnimation(bottomNavAnimation)
        return AnimationUtils.loadAnimation(
            requireContext(), androidx.navigation.ui.R.anim.nav_default_enter_anim
        )
    }

    override fun onDestroy() {
        super.onDestroy()

        val bottomNavAnimation: Animation = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.slide_up_in
        )
        bottomNav.startAnimation(bottomNavAnimation)

        bottomNav.visibility = View.VISIBLE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.player_form_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.guardar_item) {
                    if (!binding.playerNameInput.text.isNullOrBlank() && !binding.playerBalanceInput.text.isNullOrBlank()) {
                        val playerName = binding.playerNameInput.text.toString()
                        val playerBalance = binding.playerBalanceInput.text.toString().toInt()
                        Toast.makeText(requireContext(),playerName, Toast.LENGTH_SHORT).show()
                        playerViewModel.onCreatePlayer(playerName,playerBalance )

                        view.findNavController().navigate(R.id.action_playerFormFragment_to_playersFragment)
                    }else{
                        Toast.makeText(requireContext(),"que haces wachin ponele nombre", Toast.LENGTH_SHORT).show()

                    }


                }
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }


}