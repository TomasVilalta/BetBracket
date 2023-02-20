package com.example.betbracket.events.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.betbracket.abstractFragments.MainScreenAbstractFragment
import com.example.betbracket.R
import com.example.betbracket.databinding.FragmentEventsBinding
import com.example.betbracket.events.EventViewModel
import com.example.betbracket.events.adapter.EventsAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class EventsFragment : MainScreenAbstractFragment() {

    private var _binding: FragmentEventsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: EventsAdapter
    private lateinit var eventViewModel : EventViewModel
//    private var eventsList = EventProvider.getEvents()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEventsBinding.inflate(inflater, container, false)
        eventViewModel = ViewModelProvider(this)[EventViewModel::class.java]

        initRecyclerView()

        eventViewModel.eventList.observe(viewLifecycleOwner){newEventList ->
            adapter.eventsList = newEventList
            adapter.notifyDataSetChanged()
        }

        binding.addFab.setOnClickListener {
            it.findNavController().navigate(EventsFragmentDirections.actionEventsFragmentToCreateEventFragment())
        }


        return binding.root
    }

    private fun initRecyclerView() {
        //TODO replace with ViewModel fun
        adapter = EventsAdapter(eventViewModel.getEvents(),
            { event -> eventClickDelete(event)},
            { event -> eventClickSelect(event) }
        )
        binding.eventList.layoutManager = LinearLayoutManager(activity)
        binding.eventList.adapter = adapter
    }

    private fun eventClickSelect(event: Int) {
        this.findNavController().navigate(EventsFragmentDirections.actionEventsFragmentToEventDetailFragment(event))
    }

    private fun eventClickDelete(event: Int) {
        MaterialAlertDialogBuilder(
            this.requireContext(),
            R.style.AlertDialog_BetBracket
        )
            .setMessage("¿Quieres eliminar ${eventViewModel.getEvents()[event].title}?")
            .setPositiveButton("Si") { _, _ ->
                eventViewModel.onDeleteEvent(event)
            }
            .setNegativeButton("No") { _, _ ->
            }.show()

    }

}
