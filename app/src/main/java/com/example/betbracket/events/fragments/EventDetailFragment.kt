package com.example.betbracket.events.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.betbracket.R
import com.example.betbracket.abstractFragments.SecondaryScreenAbstractFragment
import com.example.betbracket.database.BetDatabase
import com.example.betbracket.database.entities.Player
import com.example.betbracket.database.relations.EventWithPlayers
import com.example.betbracket.databinding.FragmentEventDetailBinding
import com.example.betbracket.events.EventViewModel
import com.example.betbracket.events.EventViewModelProviderFactory
import com.example.betbracket.events.EventsRepository


class EventDetailFragment : SecondaryScreenAbstractFragment() {
    private var _binding: FragmentEventDetailBinding? = null
    private val binding get() = _binding!!
    private val eventViewModel: EventViewModel by viewModels({ requireParentFragment() }) {
        EventViewModelProviderFactory(
            EventsRepository(BetDatabase.getInstance(requireContext()))
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEventDetailBinding.inflate(inflater, container, false)
        val args = EventDetailFragmentArgs.fromBundle(requireArguments())

        eventViewModel.getEventWithPlayersByTitle(args.eventTitle)


        eventViewModel.currentEvent.observe(viewLifecycleOwner){
            paintViews(it)
        }


        (activity as AppCompatActivity).supportActionBar?.hide()
        animateBottomNav()


        binding.backButton.setOnClickListener {
            it.findNavController().navigateUp()

        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        eventViewModel.getPlayers().observe(viewLifecycleOwner) {playerList ->
            val dropDownAdapter =
                ArrayAdapter(requireContext(), R.layout.dropdown_item, playerList.map { list -> list.name })
            binding.eventBetForm.bettingPlayerInput.setAdapter(dropDownAdapter)

            binding.eventBetForm.bettingPlayerInput.setOnItemClickListener { adapterView, view, i, l ->
                val selectedPlayer: Player? = playerList.find { it.name == binding.eventBetForm.bettingPlayerInput.text.toString() }
                if (selectedPlayer != null) {
                    binding.eventBetForm.bettingPlayerBalanceTxt.text = roundOff(selectedPlayer.balance)
                }
            }
        }

    }
    private fun paintViews(event: EventWithPlayers) {
        binding.eventTitle.text = event.event.title
        binding.eventStateText.text = event.event.status
        Glide.with(requireContext()).load(event.player1.image).into(binding.player1Image)
        Glide.with(requireContext()).load(event.player2.image).into(binding.player2Image)
        binding.player1NameText.text = event.player1.name
        binding.player2NameText.text = event.player2.name
        binding.player1OddsText.text = roundOff(event.event.player1Return)
        binding.player2OddsText.text = roundOff(event.event.player2Return)
        binding.eventBetForm.player1Radio.text = event.event.player1Name
        binding.eventBetForm.player2Radio.text = event.event.player2Name
    }
    private fun roundOff(num: Double):String = (Math.round(num * 100.0) / 100.0).toString()


    override fun onDestroy() {
        super.onDestroy()
        (activity as AppCompatActivity).supportActionBar?.show()

    }

    override fun onPrimaryNavigationFragmentChanged(isPrimaryNavigationFragment: Boolean) {
        super.onPrimaryNavigationFragmentChanged(isPrimaryNavigationFragment)
    }
}
