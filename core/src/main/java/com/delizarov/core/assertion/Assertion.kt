package com.delizarov.core.assertion

object Assertion {

    var enabled = false
        private set

    fun initialize(enabled: Boolean) {
        this.enabled = enabled
    }
}