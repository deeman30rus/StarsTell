package com.delizarov.testsite.fragments

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.delizarov.core.FragmentViewProperty
import com.delizarov.skybox.SkyBoxView
import com.delizarov.testsite.R

class SkyBoxFragment : BaseFragment() {

    private val layout: FrameLayout by FragmentViewProperty(R.id.container)

    private lateinit var skyBoxView: SkyBoxView

    override val layoutRes: Int
        get() = R.layout.fragment_sky_box


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        skyBoxView = SkyBoxView(context!!).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layout.addView(skyBoxView)
    }
}