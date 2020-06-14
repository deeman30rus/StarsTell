package com.delizarov.parallaxview

import com.delizarov.parallaxview.physics.Vector

private const val ANIMATIONS_FRAMES = 30f

private const val G = 9.81f

class GravityViewModel(
    private val ampX: Int,
    private val ampY: Int
) {

    private var frame = 0

    private var startState = Vector()

    private val state = Vector()
    var inclination = Vector()
        set(value) {
            if (value epsEqualsTo field) return

            field = value
            startState = state

            frame = 0
        }

    val dx: Float
        get() = state.x / G * ampX

    val dy: Float
        get() = state.y / G * ampY

    fun update() {
        if (inclination epsEqualsTo state) return

        val progress = frame / ANIMATIONS_FRAMES
        state.update(progress)

        frame++
    }

    private fun Vector.update(progress: Float) {
        x += (inclination.x - startState.x) * progress
        y += (inclination.y - startState.y) * progress
    }
}

