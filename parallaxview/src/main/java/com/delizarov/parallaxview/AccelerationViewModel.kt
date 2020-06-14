package com.delizarov.parallaxview

import com.delizarov.parallaxview.physics.Vector
import kotlin.math.exp
import kotlin.math.ln

private const val EPS = 0.0001f

private const val AMP = 5
private const val FRAMES_ANIMATION = 30

private val CORRELATION = 4 * ln(10f) / FRAMES_ANIMATION

internal class AccelerationViewModel {
    private var disturbance = Vector()
    private var velocity = Vector()

    private var k = 0f
    var frame = 0

    val dx: Float
        get() = velocity.x

    val dy: Float
        get() = velocity.y

    fun disturb(disturbance: Vector) {
        this.disturbance = velocity + (disturbance * AMP)

        frame = 0
        k = 1f
    }

    fun update() {
        if (k < EPS) return

        k = f(frame)
        velocity = (disturbance * k)

        ++frame
    }

    private fun f(x: Int) = exp (-x * CORRELATION)
}