package com.delizarov.testsite.navigation

import android.os.Bundle
import com.delizarov.testsite.fragments.*
import java.lang.IllegalArgumentException

class FragmentFactory {

    inline fun <reified T: BaseFragment> create(args: Bundle = Bundle()): BaseFragment {
        val fragment = when (T::class.java) {
            MainMenuFragment::class.java -> MainMenuFragment()
            WidgetsFragment::class.java -> WidgetsFragment()
            ZodiacViewFragment::class.java -> ZodiacViewFragment()
            SkyBoxFragment::class.java -> SkyBoxFragment()
            else -> throw IllegalArgumentException("Fragment key ${T::class} not supported")
        }

        fragment.arguments = args
        return fragment
    }
}