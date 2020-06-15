package com.delizarov.zodiacview.graph

import androidx.annotation.IntDef
import com.delizarov.zodiacview.ZodiacSign
import kotlin.random.Random

internal const val STAR_SIZE_BIG = 1
internal const val STAR_SIZE_MEDIUM = 2
internal const val STAR_SIZE_SMALL = 3

@Retention(AnnotationRetention.SOURCE)
@IntDef(
    STAR_SIZE_BIG,
    STAR_SIZE_MEDIUM,
    STAR_SIZE_SMALL
)
internal annotation class StarSize

internal class Star(
    val x: Int,
    val y: Int,
    @StarSize val size: Int,
    val type: Int = Random.nextInt(starDrawableRes.size)
)

internal class ZodiacGraph private constructor (
    builder: Builder
) {

    val sign: ZodiacSign = builder.sign
    val stars: List<Star> = builder.stars
    val edges: List<Pair<Int, Int>> = builder.edges

    class Builder {
        lateinit var sign: ZodiacSign
        lateinit var stars: List<Star>
        lateinit var edges: List<Pair<Int, Int>>

        fun build() = ZodiacGraph(this)
    }

    companion object {
        inline fun zodiac(block: Builder.() -> Unit) = Builder().apply(block).build()
    }
}

internal fun star(x: Int, y: Int, @StarSize sizeType: Int) = Star(x, y, sizeType)

internal fun stars(vararg stars: Star) = stars.toList()

internal fun edges(vararg edge: Pair<Int, Int>) = edge.toList()