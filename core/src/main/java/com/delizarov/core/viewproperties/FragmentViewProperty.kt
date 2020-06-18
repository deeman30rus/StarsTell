package com.delizarov.core.viewproperties

import android.view.View
import androidx.annotation.IdRes
import com.delizarov.core.fragments.BaseFragment
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class FragmentViewProperty<T : View>(
    fragment: BaseFragment,
    @IdRes private val propertyId: Int
) : ReadOnlyProperty<BaseFragment, T> {

    init {
        fragment.requestView(propertyId)
    }

    override fun getValue(thisRef: BaseFragment, property: KProperty<*>): T =
        thisRef.retrieveView(propertyId)

}
