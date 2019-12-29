package com.delizarov.testsite.navigation

import android.os.Bundle

interface Navigator {

    fun navigateToMainMenu(args: Bundle = Bundle())

    fun navigateToWidgets(args: Bundle = Bundle())

    fun navigateToZodiacView(args: Bundle = Bundle())

    fun navigateToSkyBox(args: Bundle = Bundle())
}