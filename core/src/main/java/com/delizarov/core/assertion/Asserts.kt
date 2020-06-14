package com.delizarov.core.assertion

import com.delizarov.core.BuildConfig
import java.lang.AssertionError

inline fun assertTrue(condition: Boolean, message: () -> String) {
    if (BuildConfig.DEBUG) {
        if (!condition) fail(message)
    }
}

inline fun fail (message: () -> String) {
    if (Assertion.enabled) throw AssertionError(message())
}

