package com.example.betbracket.events.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.betbracket.MainScreenAbstractFragment
import com.example.betbracket.R
import com.example.betbracket.databinding.FragmentEventsBinding
import com.example.betbracket.events.adapter.EventsAdapter
import com.example.betbracket.events.providers.EventProvider


class EventsFragment : MainScreenAbstractFragment() {

    private var _binding: FragmentEventsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: EventsAdapter
//    private var eventsList = EventProvider.getEvents()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEventsBinding.inflate(inflater, container, false)

        initRecyclerView()

        binding.addFab.setOnClickListener {
            it.findNavController().navigate(R.id.action_eventsFragment_to_createEventFragment)
        }


        return binding.root
    }

    private fun initRecyclerView() {
        adapter = EventsAdapter(EventProvider.getEvents())
        binding.eventList.layoutManager= LinearLayoutManager(activity)
        binding.eventList.adapter = adapter
    }

}
