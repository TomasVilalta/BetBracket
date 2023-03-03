package com.example.betbracket.events.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
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
import com.example.betbracket.events.adapter.EventBetsAdapter
import com.google.android.material.radiobutton.MaterialRadioButton


class EventDetailFragment : SecondaryScreenAbstractFragment() {
    private var _binding: FragmentEventDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: EventBetsAdapter
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
        eventViewModel.getEventWithBetsByTitle(args.eventTitle)

        initRecyclerView()

        eventViewModel.currentEventAndPlayers.observe(viewLifecycleOwner) {
            paintViews(it)
        }

        eventViewModel.currentEventAndBets.observe(viewLifecycleOwner) {
            adapter.differ.submitList(it.bets)
        }

        eventViewModel.returnDifference.observe(viewLifecycleOwner) {
            val paddingMap = mapOf(
                0 to 4,
                1 to 5,
                2 to 6,
                3 to 7,
            ).withDefault { 4 }

            val elevationMap = mapOf(
                0 to 1,
                1 to 2,
                2 to 4,
                3 to 6,
            ).withDefault { 1 }

            val colorMap = mapOf(
                0 to false,
                1 to true,
                2 to true,
                3 to true,
            ).withDefault { false }

            val padding = paddingMap.getValue(it.second)
            val elevation = elevationMap.getValue(it.second)
            val color = colorMap.getValue(it.second)

            val bigLayout = if (!it.first) {
                binding.player1OddsLayout
            } else {
                binding.player2OddsLayout
            }
            val smallLayout = if (!it.first) {
                binding.player2OddsLayout
            } else {
                binding.player1OddsLayout
            }
            val bigText = if (!it.first) {
                binding.player1OddsText
            } else {
                binding.player2OddsText
            }
            val smallText = if (!it.first) {
                binding.player2OddsText
            } else {
                binding.player1OddsText
            }

            bigLayout.setPadding(padding, padding, padding, padding)
            bigLayout.elevation = elevation.toFloat()
            smallLayout.setPadding(4, 4, 4, 4)
            smallLayout.elevation = 1.0F
            if (color) {
                bigText.setTextColor(ContextCompat.getColor(requireContext(), R.color.successColor))
                smallText.setTextColor(ContextCompat.getColor(requireContext(), R.color.error))
            } else {
                bigText.setTextColor(ContextCompat.getColor(requireContext(), R.color.onBackground))
                smallText.setTextColor(ContextCompat.getColor(requireContext(), R.color.onBackground))
            }

        }


        (activity as AppCompatActivity).supportActionBar?.hide()
        animateBottomNav()


        binding.eventBetForm.betButton.setOnClickListener {
            var flag: Boolean = false
            binding.eventBetForm.apply {
                if (bettingPlayerInput.text.isNullOrBlank()) {
                    flag = true
                }

                if (rgWinnerSelect.checkedRadioButtonId == -1) {
                    flag = true
                }

                if (amountEditText.text.isNullOrBlank()) {
                    flag = true
                }

                if (!flag) {
                    val checkedPredictionId = rgWinnerSelect.checkedRadioButtonId
                    val checkedPrediction =
                        rgWinnerSelect.findViewById<MaterialRadioButton>(checkedPredictionId)
                    Log.i("PLACE A BET", "Radio button is -> $checkedPredictionId")
                    eventViewModel.onPlaceBet(
                        bettingPlayerInput.text.toString(),
                        amountEditText.text.toString().toDouble(),
                        checkedPrediction.text.toString()
                    )
                    Toast.makeText(requireContext(), "Apuesta guardada", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Completa todos los campos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        binding.backButton.setOnClickListener {
            it.findNavController().navigateUp()
        }

        return binding.root
    }

    private fun initRecyclerView() {
        adapter = EventBetsAdapter()
        binding.eventBetForm.betsRecyclerView.adapter = adapter
        binding.eventBetForm.betsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onResume() {
        super.onResume()
        eventViewModel.getPlayers().observe(viewLifecycleOwner) { playerList ->
            val dropDownAdapter =
                ArrayAdapter(
                    requireContext(),
                    R.layout.dropdown_item,
                    playerList.map { list -> list.name })
            binding.eventBetForm.bettingPlayerInput.setAdapter(dropDownAdapter)

            //Paint Betting player balance
            binding.eventBetForm.bettingPlayerInput.setOnItemClickListener { adapterView, view, i, l ->
                val selectedPlayer: Player? =
                    playerList.find { it.name == binding.eventBetForm.bettingPlayerInput.text.toString() }
                if (selectedPlayer != null) {
                    binding.eventBetForm.bettingPlayerBalanceTxt.text =
                        getString(R.string.playerBalance, roundOff(selectedPlayer.balance))
                }
            }

        }

    }

    private fun paintViews(event: EventWithPlayers) {
        binding.apply {
            // Text
            eventTitle.text = event.event.title
            eventStateText.text = event.event.status
            player1NameText.text = event.player1.name
            player2NameText.text = event.player2.name
            player1OddsText.text = roundOff(event.event.player1Return)
            player2OddsText.text = roundOff(event.event.player2Return)
            eventViewModel.setReturnDifference(event.event.player1Return,event.event.player2Return)
            eventBetForm.player1Radio.text = event.event.player1Name
            eventBetForm.player2Radio.text = event.event.player2Name
            //Player Images
            Glide.with(requireContext()).load(event.player1.image).into(player1Image)
            Glide.with(requireContext()).load(event.player2.image).into(player2Image)

        }

    }

    private fun roundOff(num: Double): String = (Math.round(num * 100.0) / 100.0).toString()


    override fun onDestroy() {
        super.onDestroy()
        (activity as AppCompatActivity).supportActionBar?.show()

    }

}
