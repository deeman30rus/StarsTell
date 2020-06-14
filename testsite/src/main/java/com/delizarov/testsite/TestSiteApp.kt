package com.delizarov.testsite

import android.app.Application
import com.delizarov.core.assertion.Assertion

class TestSiteApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Assertion.initialize(true)
    }
}