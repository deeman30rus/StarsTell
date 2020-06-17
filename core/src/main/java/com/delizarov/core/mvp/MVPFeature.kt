package com.delizarov.core.mvp

import com.delizarov.core.Subscription

class MVPFeature <TView: IView, TPresenter : Presenter<TView>> (
    private val presenter: TPresenter,
    private val view: TView
) {

    private var subscription: Subscription? = null

    private lateinit var lifecycleOwner: LifecycleOwner

    fun insertInto(lifecycleOwner: LifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner
    }

    fun enable() {
        presenter.attachView(view)

        subscription = lifecycleOwner.subscribe(presenter)
    }

    fun disable() {
        presenter.detachView()

        subscription?.close()
        subscription = null
    }
    
}