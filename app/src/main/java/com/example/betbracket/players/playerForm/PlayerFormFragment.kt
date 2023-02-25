package com.example.betbracket.players.playerForm

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.betbracket.R
import com.example.betbracket.abstractFragments.SecondaryScreenAbstractFragment
import com.example.betbracket.database.BetDatabase
import com.example.betbracket.database.entities.Player
import com.example.betbracket.databinding.FragmentPlayerFormBinding
import com.example.betbracket.players.PlayerViewModel
import com.example.betbracket.players.PlayerViewModelProviderFactory
import com.example.betbracket.players.PlayersRepository
import kotlinx.coroutines.launch


class PlayerFormFragment : SecondaryScreenAbstractFragment() {

    private var _binding: FragmentPlayerFormBinding? = null
    private val binding get() = _binding!!
    private lateinit var args: PlayerFormFragmentArgs
    private val playerViewModel: PlayerViewModel by viewModels({ requireParentFragment() }){PlayerViewModelProviderFactory(
        PlayersRepository(BetDatabase.getInstance(requireContext()))
    )}
    private var newPlayer: Player? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerFormBinding.inflate(inflater, container, false)

        playerViewModel.currentPlayerImage.observe(viewLifecycleOwner) { newImage ->
            Log.i("AVATAR", "OBSERVED")
            Glide.with(this).load(newImage).into(binding.playerImage)
        }

        // Default playerPos is -1, which means the user is creating a new player
        args = PlayerFormFragmentArgs.fromBundle(requireArguments())
        Log.i("ARGS", "${args.playerName}")

        if (args.playerName != null) {
            lifecycleScope.launch {
                newPlayer = playerViewModel.getPlayerByName(args.playerName!!)
                fillPlayerFields(newPlayer!!)
            }
        } else {
            playerViewModel.setCurrentPlayerImage("default")
        }

        animateBottomNav()
        setUpToolbar()

        binding.avatarButton.setOnClickListener {
            AvatarDialogFragment().show(childFragmentManager, AvatarDialogFragment.TAG)
        }

        return binding.root
    }

    private fun fillPlayerFields(player: Player) {
        binding.playerNameInput.setText(player.name)
        binding.playerBalanceInput.setText(player.balance.toString())
        playerViewModel.setCurrentPlayerImage(player.image)
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
                        val playerBalance = binding.playerBalanceInput.text.toString().toDouble()
                        if (newPlayer == null) {
                             playerViewModel.currentPlayerImage.value?.let {
                                 val newPlayer = Player(playerName, playerBalance, it)
                                playerViewModel.onCreatePlayer(newPlayer)
                            }

                            Toast.makeText(
                                requireContext(),
                                "Jugador creado",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            newPlayer!!.let {
                                it.name = playerName
                                it.balance = playerBalance
                                it.image = playerViewModel.currentPlayerImage.value!!
                                playerViewModel.onEditPlayer(it)
                            }
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
            val imm =
                (activity as AppCompatActivity).getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }

    }


}
