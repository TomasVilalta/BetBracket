package com.example.betbracket.abstractFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.betbracket.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView


abstract class SecondaryScreenAbstractFragment : Fragment() {
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var toolBar: MaterialToolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_secondary_screen_abstract, container, false)
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

    fun setUpToolbar() {
        val toolBarAnimation: Animation = animateBottomNav()
        toolBar = (activity as AppCompatActivity).findViewById<MaterialToolbar>(R.id.topAppBar)
        toolBar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_close_24)
        toolBar.setNavigationIconTint(
            ContextCompat.getColor(
                requireContext(), R.color.onBackground
            )
        )
        toolBar.startAnimation(toolBarAnimation)
    }

    fun animateBottomNav(): Animation {
        bottomNav =
            (activity as AppCompatActivity).findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.visibility = View.GONE
        val bottomNavAnimation: Animation = AnimationUtils.loadAnimation(
            requireContext(), R.anim.slide_down_out
        )
        bottomNav.startAnimation(bottomNavAnimation)
        return AnimationUtils.loadAnimation(
            requireContext(), androidx.navigation.ui.R.anim.nav_default_enter_anim
        )
    }

}