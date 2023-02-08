package com.example.betbracket.players

import android.app.Dialog
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
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
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.android.awaitFrame


class PlayerFormFragment : Fragment() {

    private var _binding: FragmentPlayerFormBinding? = null
    private val binding get() = _binding!!
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var toolBar: MaterialToolbar


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout to use as dialog or embedded fragment
        _binding = FragmentPlayerFormBinding.inflate(inflater, container, false)

        animateBottomNav()
        setUpToolbar()


        return binding.root
    }

    private fun setUpToolbar() {
        val toolBarAnimation: Animation = animateBottomNav()
        toolBar = (activity as AppCompatActivity).findViewById<MaterialToolbar>(R.id.topAppBar)
        toolBar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_close_24)
        toolBar.setNavigationIconTint(
            ContextCompat.getColor(
                requireContext(),
                R.color.onBackground
            )
        )
        toolBar.startAnimation(toolBarAnimation)
    }

    private fun animateBottomNav(): Animation {
        bottomNav =
            (activity as AppCompatActivity).findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.visibility = View.GONE
        val bottomNavAnimation: Animation = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.slide_down_out
        )
        bottomNav.startAnimation(bottomNavAnimation)
        return AnimationUtils.loadAnimation(
            requireContext(),
            androidx.navigation.ui.R.anim.nav_default_enter_anim
        )
    }

    override fun onDestroy() {
        super.onDestroy()

        val bottomNavAnimation: Animation = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.slide_up_in
        )
        bottomNav.startAnimation(bottomNavAnimation)

        bottomNav.visibility = View.VISIBLE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.player_form_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.guardar_item) {
                    Toast.makeText(requireContext(), "hola", Toast.LENGTH_SHORT).show()
                }
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }


}