package com.delizarov.core.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.delizarov.core.Subscription
import com.delizarov.core.mvp.LifecycleListener
import com.delizarov.core.mvp.LifecycleOwner
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

abstract class BaseFragment : Fragment(), LifecycleOwner {

    private val subscribers = mutableListOf<LifecycleListener>()

    private val viewProperties = mutableMapOf<Int, View>()
    private val viewRequests = mutableListOf<Int>()

    protected abstract val layoutRes: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutRes, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViewProperties()

        subscribers.notifyOnCreated()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        unbindViewProperties()

        subscribers.notifyOnDestroyed()
    }

    override fun onStart() {
        super.onStart()

        subscribers.notifyOnStarted()
    }

    override fun onResume() {
        super.onResume()

        subscribers.notifyOnResumed()
    }

    override fun onPause() {
        super.onPause()

        subscribers.notifyOnPaused()
    }

    override fun onStop() {
        super.onStop()

        subscribers.notifyOnStoped()
    }

    override fun onDestroy() {
        super.onDestroy()

        subscribers.notifyOnDestroyed()
    }

    override fun subscribe(listener: LifecycleListener): Subscription = LifecycleSubscription(listener)

    private fun bindViewProperties() {
        for (id in viewRequests) {
            viewProperties[id] = view?.findViewById(id) ?: throw IllegalArgumentException("View with $id doesn't exist")
        }

    }

    private fun unbindViewProperties() {
        viewRequests.clear()
    }

    internal fun requestView(@IdRes viewId: Int) {
        viewRequests.add(viewId)
    }

    internal fun <T: View> retrieveView(@IdRes viewId: Int): T {
        return viewProperties[viewId] as? T ?: throw IllegalStateException("Cannot cast to give type")
    }

    private fun List<LifecycleListener>.notifyOnCreated() = forEach { it.onCreated() }

    private fun List<LifecycleListener>.notifyOnStarted() = forEach { it.onStarted() }

    private fun List<LifecycleListener>.notifyOnResumed() = forEach { it.onResumed() }

    private fun List<LifecycleListener>.notifyOnPaused() = forEach { it.onPaused() }

    private fun List<LifecycleListener>.notifyOnStoped() = forEach { it.onStoped() }

    private fun List<LifecycleListener>.notifyOnDestroyed() = forEach { it.onDestroyed() }


    private inner class LifecycleSubscription(
        private val subscriber: LifecycleListener
    ) : Subscription {

        init {
            subscribers.add(subscriber)
        }

        override fun close() {
            subscribers.remove(subscriber)
        }
    }
}