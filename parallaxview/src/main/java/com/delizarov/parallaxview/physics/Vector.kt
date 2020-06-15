package com.delizarov.parallaxview.physics

import kotlin.math.abs

private const val EPS = 10e-4

internal class Vector(
    var x: Float = 0f,
    var y: Float = 0f
) {
    operator fun times(k: Float) = Vector(this.x * k, this.y * k)

    operator fun times(k: Int) = Vector(this.x * k, this.y * k)

    operator fun plus(other: Vector) = Vector(this.x + other.x, this.y + other.y)

    operator fun minus(other: Vector) = Vector(this.x - other.x, this.y - other.y)

    infix fun epsEqualsTo(other: Vector): Boolean {
        return abs(this.x - other.x) < EPS && abs(this.y - other.y) < EPS
    }
}