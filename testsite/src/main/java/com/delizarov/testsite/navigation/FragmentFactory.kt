package com.delizarov.testsite.navigation

import android.os.Bundle
import com.delizarov.testsite.fragments.BaseFragment
import com.delizarov.testsite.fragments.MainMenuFragment
import com.delizarov.testsite.fragments.WidgesFragment
import java.lang.IllegalArgumentException

class FragmentFactory {

    inline fun <reified T: BaseFragment> create(args: Bundle = Bundle()): BaseFragment {
        val fragment = when (T::class.java) {
            MainMenuFragment::class.java -> MainMenuFragment()
            WidgesFragment::class.java -> WidgesFragment()
            else -> throw IllegalArgumentException("Fragment key ${T::class} not supported")
        }

        fragment.arguments = args
        return fragment
    }
}