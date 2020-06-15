package com.delizarov.testsite.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.delizarov.testsite.TestSiteActivity
import com.delizarov.testsite.navigation.Navigator

abstract class BaseFragment: Fragment() {

    protected lateinit var navigator: Navigator

    abstract val layoutRes: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navigator = (activity as TestSiteActivity).navigator
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(layoutRes, container, false)
}