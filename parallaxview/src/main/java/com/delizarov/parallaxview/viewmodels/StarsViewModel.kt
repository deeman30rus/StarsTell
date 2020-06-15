package com.delizarov.parallaxview.viewmodels

import android.graphics.Rect
import kotlin.random.Random

internal class StarsViewModel(
    generator: () -> List<Star>
) {
    val stars = generator.invoke()
    private val active = stars.filter { it.active }

    fun update() {
        active.forEach { star ->
            if (star.dim) star.dim()
            else star.shine()
        }
    }
}

internal class Star(
    var x: Float,
    var y: Float,
    var alpha: Float,
    val active: Boolean,
    var dim: Boolean
) {

    fun dim() {
        alpha -= 0.03f

        if (alpha < 0.04) {
            dim = false
        }
    }

    fun shine() {
        alpha += 0.03f

        if (alpha > 0.96) {
            dim = true
        }
    }

    companion object {

        fun random(area: Rect) = Star(
            Random.nextFloat() * area.width() + area.left,
            Random.nextFloat() * area.height() + area.top,
            Random.nextFloat(),
            Random.nextInt(100) > 65,
            Random.nextBoolean()
        )
    }
}
