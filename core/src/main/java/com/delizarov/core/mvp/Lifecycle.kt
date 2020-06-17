package com.delizarov.core.mvp

import com.delizarov.core.Subscription

interface LifecycleOwner {

    fun subscribe(listener: LifecycleListener): Subscription
}

interface LifecycleListener {

    fun onCreated() = Unit

    fun onStarted() = Unit

    fun onResumed() = Unit

    fun onPaused() = Unit

    fun onStoped() = Unit

    fun onDestroyed() = Unit
}