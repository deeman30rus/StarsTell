package com.delizarov.testsite.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import com.delizarov.core.FragmentViewProperty
import com.delizarov.testsite.R

class MainMenuFragment : BaseFragment() {

    private val widgets: Button by FragmentViewProperty(R.id.btn_widgets)

    override val layoutRes: Int
        get() = R.layout.fragment_main_menu

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        widgets.setOnClickListener {
            navigator.navigateToWidgets()
        }
    }
}