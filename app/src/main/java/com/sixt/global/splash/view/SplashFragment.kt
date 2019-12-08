package com.sixt.global.splash.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.sixt.global.R
import kotlinx.android.synthetic.main.fragment_splash.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            delay(SPLASH_TIME)

            goToCarsMap()
        }
    }

    private fun goToCarsMap() {
        findNavController().navigate(R.id.action_splashFragment_to_carsFragment, null, null,
            FragmentNavigatorExtras(
                iv_logo_icon to TRANSITION_NAME
            )
        )
    }

    companion object {
        const val SPLASH_TIME = 2000L
        const val TRANSITION_NAME = "logo"
    }
}