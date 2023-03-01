package com.example.betbracket.events.fragments

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.betbracket.R
import com.example.betbracket.abstractFragments.SecondaryScreenAbstractFragment
import com.example.betbracket.database.BetDatabase
import com.example.betbracket.database.entities.Event
import com.example.betbracket.database.relations.EventWithPlayers
import com.example.betbracket.databinding.FragmentEventDetailBinding
import com.example.betbracket.events.EventViewModel
import com.example.betbracket.events.EventViewModelProviderFactory
import com.example.betbracket.events.EventsRepository
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch


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

    private fun paintViews(event: EventWithPlayers) {
        binding.eventTitle.text = event.event.title
        binding.eventStateText.text = event.event.status
        Glide.with(requireContext()).load(event.player1.image).into(binding.player1Image)
        Glide.with(requireContext()).load(event.player2.image).into(binding.player2Image)
        binding.player1NameText.text = event.player1.name
        binding.player2NameText.text = event.player2.name
        binding.player1OddsText.text = roundOff(event.event.player1Return)
        binding.player2OddsText.text = roundOff(event.event.player2Return)
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
