package com.example.betbracket.players.playerForm

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.betbracket.R
import com.example.betbracket.databinding.FragmentPlayerFormBinding
import com.example.betbracket.players.PlayerViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView


class PlayerFormFragment : Fragment() {

    private var _binding: FragmentPlayerFormBinding? = null
    private val binding get() = _binding!!
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var toolBar: MaterialToolbar
    private lateinit var args: PlayerFormFragmentArgs
    private val playerViewModel: PlayerViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout to use as dialog or embedded fragment
        _binding = FragmentPlayerFormBinding.inflate(inflater, container, false)

        playerViewModel.playerImage.observe(viewLifecycleOwner){newImage ->
            Log.i("AVATAR", "OBSERVED")
            Glide.with(this).load(newImage).into(binding.playerImage)
        }
        // Default playerPos is -1, which means the user is creating a new player
        args = PlayerFormFragmentArgs.fromBundle(requireArguments())
        Log.i("ARGS", "${args.playerPos}")

        if (args.playerPos != -1) {
            fillPlayerFields()
        }

        animateBottomNav()
        setUpToolbar()



        binding.avatarButton.setOnClickListener{
           AvatarDialogFragment().show(childFragmentManager, AvatarDialogFragment.TAG)
        }

        return binding.root
    }

    private fun fillPlayerFields() {
        binding.playerNameInput.setText(playerViewModel.getPlayerName(args.playerPos))
        binding.playerBalanceInput.setText(playerViewModel.getPlayerBalance(args.playerPos).toString())
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
                        if (args.playerPos == -1) {
                            playerViewModel.onCreatePlayer(playerName, playerBalance)
                            Toast.makeText(
                                requireContext(),
                                "Jugador creado",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            playerViewModel.onEditPlayer(args.playerPos, playerName, playerBalance)
                            Toast.makeText(
                                requireContext(),
                                "Jugador actualizado",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        closeKeyboard()
                        view.findNavController()
                            .navigate(PlayerFormFragmentDirections.actionPlayerFormFragmentToPlayersFragment())

                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Nombre y balance deben estar completos",
                            Toast.LENGTH_SHORT
                        ).show()

                    }


                }
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun closeKeyboard() {
        this.binding.playerBalanceInput?.let { view ->
            val imm = (activity as AppCompatActivity).getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }

    }


}
