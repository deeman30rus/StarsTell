package com.delizarov.core.mvp

interface IView

abstract class Presenter<T : IView> : LifecycleListener {

    protected var view: T? = null

    fun attachView(view: T) {
        this.view = view
    }

    fun detachView() {
        this.view = null
    }
}