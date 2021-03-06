// Copyright (c) 2019 Yandex LLC. All rights reserved.
// Author: Dmitrii Yelizarov <the-room-101@yandex-team.ru>

package com.delizarov.zodiacview

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import androidx.annotation.IntDef
import androidx.core.animation.doOnEnd

typealias OnSignChanged = (ZodiacSign) -> Unit

internal const val DIRECTION_TO_PREV = 1
internal const val DIRECTION_TO_NEXT = 2

@Retention(AnnotationRetention.SOURCE)
@IntDef(
    DIRECTION_TO_PREV,
    DIRECTION_TO_NEXT
)
internal annotation class TransitionDirection

class ZodiacView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    View(context, attrs, defStyleAttr) {

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    var sign = ZodiacSign.Pisces
    private set
    private var viewState: ViewState = StaticViewState(context.resources, sign)

    private var renderer = Renderer(context.resources)

    private var onSignChanged: OnSignChanged? = null

    override fun onDraw(canvas: Canvas?) {
        canvas?.let {
            renderer.drawFrame(it, viewState)
        }
    }

    fun setOnSignChanged(callback: OnSignChanged) {
        onSignChanged = callback
    }

    fun nextSign() {
        ValueAnimator.ofInt(100).apply {
            doOnEnd {
                sign = sign.next()
                onSignChanged?.invoke(sign)
            }
            addUpdateListener { animator ->
                viewState = AnimationViewState(
                    resources,
                    animator.animatedValue as Int,
                    sign,
                    DIRECTION_TO_NEXT
                )

                invalidate()
            }
            duration = 300
        }.start()
    }

    fun prevSign() {
        ValueAnimator.ofInt(100).apply {
            doOnEnd {
                sign = sign.prev()
                onSignChanged?.invoke(sign)
            }
            addUpdateListener { animator ->
                viewState = AnimationViewState(
                    resources,
                    animator.animatedValue as Int,
                    sign,
                    DIRECTION_TO_PREV
                )

                invalidate()
            }
            duration = 300
        }.start()
    }
}
