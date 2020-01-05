package com.delizarov.testsite.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import com.delizarov.core.FragmentViewProperty
import com.delizarov.testsite.R

class WidgetsFragment : BaseFragment() {

    private val zodiacView: Button by FragmentViewProperty(R.id.zodiac_view)
    private val skyBox: Button by FragmentViewProperty(R.id.sky_box)
    private val parallaxView: Button by FragmentViewProperty(R.id.parallax_view)

    override val layoutRes: Int
        get() = R.layout.fragment_widgets

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        zodiacView.setOnClickListener {
            navigator.navigateToZodiacView()
        }

        skyBox.setOnClickListener {
            navigator.navigateToSkyBox()
        }

        parallaxView.setOnClickListener {
            navigator.navigateToParallaxView()
        }
    }
}