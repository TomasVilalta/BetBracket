package com.example.betbracket.events.fragments

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import com.example.betbracket.R
import com.example.betbracket.databinding.FragmentCreateEventBinding
import com.example.betbracket.events.EventViewModel


class CreateEventFragment : Fragment() {

    private var _binding: FragmentCreateEventBinding? = null
    private val binding get() = _binding!!
    private val eventViewModel: EventViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateEventBinding.inflate(layoutInflater, container, false)

        return binding.root

    }

    override fun onResume() {
        super.onResume()
        val players = eventViewModel.getPlayerNameList()
        val dropDownAdapter = ArrayAdapter(requireContext(),R.layout.dropdown_item, players)
        binding.player1Input.setAdapter(dropDownAdapter)
        binding.player2Input.setAdapter(dropDownAdapter)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.player_form_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.guardar_item) {
                    binding.apply {
                        if (eventTitleInput.text.isNullOrBlank() || player2Input.text.isNullOrBlank() || player1Input.text.isNullOrBlank()) {
                            Toast.makeText(
                                requireContext(),
                                "Completa todos los campos",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            eventViewModel.onCreateEvent(
                                eventTitleInput.text.toString(),
                                player1Input.text.toString(),
                                player2Input.text.toString()
                            )
                            Toast.makeText(requireContext(), "Evento creado", Toast.LENGTH_SHORT).show()
                            view.findNavController().navigate(CreateEventFragmentDirections.actionCreateEventFragmentToEventsFragment())

                        }
                    }

                }
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

}





