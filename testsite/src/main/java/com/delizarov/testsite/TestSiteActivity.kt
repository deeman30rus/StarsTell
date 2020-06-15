package com.delizarov.testsite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.delizarov.testsite.fragments.*
import com.delizarov.testsite.navigation.FragmentFactory
import com.delizarov.testsite.navigation.Navigator

class TestSiteActivity : AppCompatActivity() {

    val navigator: Navigator = NavigatorImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_site)

        navigator.navigateToMainMenu()
    }

    private fun navigateTo(fragment: BaseFragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

    private inner class NavigatorImpl : Navigator {

        private val factory = FragmentFactory()

        override fun navigateToMainMenu(args: Bundle) {
            val fragment = factory.create<MainMenuFragment>(args)

            navigateTo(fragment)
        }

        override fun navigateToWidgets(args: Bundle) {
            val fragment = factory.create<WidgetsFragment>(args)

            navigateTo(fragment)
        }

        override fun navigateToZodiacView(args: Bundle) {
            val fragment = factory.create<ZodiacViewFragment>(args)

            navigateTo(fragment)
        }

        override fun navigateToSkyBox(args: Bundle) {
            val fragment = factory.create<SkyBoxFragment>(args)

            navigateTo(fragment)
        }

        override fun navigateToParallaxView(args: Bundle) {
            val fragment = factory.create<ParallaxViewFragment>(args)

            navigateTo(fragment)
        }
    }
}
