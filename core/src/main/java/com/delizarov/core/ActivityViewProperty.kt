package com.delizarov.core

import android.app.Activity
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import java.lang.IllegalArgumentException
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class ActivityViewProperty<T : View>(
    @IdRes private val propertyId: Int
) : ReadOnlyProperty<Activity, T> {

    private lateinit var view: T

    override fun getValue(thisRef: Activity, property: KProperty<*>): T =
        if (::view.isInitialized) {
            view
        } else {
            view = thisRef.findViewById(propertyId)
            view
        }
}

class FragmentViewProperty<T : View>(
    @IdRes private val propertyId: Int
) : ReadOnlyProperty<Fragment, T> {


    override fun getValue(thisRef: Fragment, property: KProperty<*>): T =
        thisRef.view?.findViewById(propertyId)
            ?: throw IllegalArgumentException("view does not exist")
}