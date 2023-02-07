package com.example.betbracket.players

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.example.betbracket.R
import com.example.betbracket.databinding.FragmentPlayerFormBinding
import com.google.android.material.appbar.MaterialToolbar


class PlayerFormFragment : Fragment() {

    private var _binding : FragmentPlayerFormBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout to use as dialog or embedded fragment
        _binding = FragmentPlayerFormBinding.inflate(inflater, container, false)




        return binding.root
    }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.player_form_menu, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.guardar_item){
                    Toast.makeText(requireContext(),"hola",Toast.LENGTH_SHORT).show()
                }
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }


}