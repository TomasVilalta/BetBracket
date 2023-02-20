package com.example.betbracket.events.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.betbracket.abstractFragments.SecondaryScreenAbstractFragment
import com.example.betbracket.databinding.FragmentEventDetailBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class EventDetailFragment : SecondaryScreenAbstractFragment() {
    private var _binding: FragmentEventDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var bottomNav: BottomNavigationView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEventDetailBinding.inflate(inflater, container, false)
        val args = EventDetailFragmentArgs.fromBundle(requireArguments())


        (activity as AppCompatActivity).supportActionBar?.hide()
        animateBottomNav()

        binding.backButton.setOnClickListener {
            it.findNavController().navigateUp()

        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as AppCompatActivity).supportActionBar?.show()

    }

    override fun onPrimaryNavigationFragmentChanged(isPrimaryNavigationFragment: Boolean) {
        super.onPrimaryNavigationFragmentChanged(isPrimaryNavigationFragment)
    }
}
