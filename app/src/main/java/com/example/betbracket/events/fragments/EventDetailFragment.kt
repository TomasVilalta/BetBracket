package com.example.betbracket.events.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.betbracket.R
import com.example.betbracket.abstractFragments.SecondaryScreenAbstractFragment
import com.example.betbracket.databinding.FragmentEventDetailBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView


class EventDetailFragment : SecondaryScreenAbstractFragment() {
    private var _binding: FragmentEventDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var bottomNav : BottomNavigationView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEventDetailBinding.inflate(inflater,container,false)
        val args = EventDetailFragmentArgs.fromBundle(requireArguments())


        (activity as AppCompatActivity).supportActionBar?.hide()
        animateBottomNav()

        binding.backButton.setOnClickListener{
            it.findNavController().navigateUp()
            (activity as AppCompatActivity).supportActionBar?.show()
        }

        return binding.root
    }



    }
