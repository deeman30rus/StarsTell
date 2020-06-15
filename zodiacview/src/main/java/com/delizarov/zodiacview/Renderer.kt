package com.delizarov.zodiacview

import android.content.res.Resources
import android.graphics.*
import com.delizarov.zodiacview.graph.STAR_SIZE_BIG
import com.delizarov.zodiacview.graph.STAR_SIZE_MEDIUM
import com.delizarov.zodiacview.graph.STAR_SIZE_SMALL
import com.delizarov.zodiacview.graph.ZodiacGraphs
import com.delizarov.zodiacview.graph.starDrawableRes

internal class Renderer(
    resources: Resources
) {

    private val zodiacSignPaint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.STROKE
        strokeWidth = 1f//resources.getDimension(R.dimen.zodiac_view_const_1dp)
    }

    private val viewTextColor = resources.getColor(R.color.zodiac_view_text_color)
    private val viewTextSize = resources.getDimension(R.dimen.zodiac_view_text_size)
    private val marginTop = resources.getDimension(R.dimen.zodiac_view_main_sign_top_margin)
    private val marginText = resources.getDimension(R.dimen.zodiac_view_main_sign_text_margin)

    private val bigStarRadius = resources.getDimension(R.dimen.zodiac_view_const_16dp)
    private val mediumStarRadius = resources.getDimension(R.dimen.zodiac_view_const_8dp)
    private val smallStarRadius = resources.getDimension(R.dimen.zodiac_view_const_6dp)

    private val starDrawables = starDrawableRes.map { resId -> resources.getDrawable(resId) }

    fun drawFrame(canvas: Canvas, viewState: ViewState) {
        clear(canvas)

        drawText(canvas, viewState.text, viewState.textAlpha)

        viewState.zodiacs.forEach { vs ->
            val rect = calculateZodiacRect(canvas, vs)
            drawZodiac(canvas, vs.sign, rect, vs.scale, vs.alpha)
        }
    }

    private fun clear(canvas: Canvas) {
        canvas.drawColor(Color.TRANSPARENT)
    }

    private fun drawText(canvas: Canvas, text: String, textAlpha: Int) {
        val paint = Paint().apply {
            color = viewTextColor
            alpha = textAlpha
            textAlign = Paint.Align.CENTER
            textSize = viewTextSize
        }

        val rect = Rect()
        canvas.getClipBounds(rect)

        val x = rect.centerX().toFloat()
        val y = rect.bottom - 40f
        canvas.drawText(text, x, y, paint)
    }

    private fun drawZodiac(
        canvas: Canvas,
        sign: ZodiacSign,
        rect: ZodiacRect,
        scaleFactor: Float,
        opacity: Int
    ) {
        val graph = ZodiacGraphs[sign]

        zodiacSignPaint.alpha = opacity

        val scale = rect.size / 100

        graph.stars.forEach { star ->
            val radius = when (star.size) {
                STAR_SIZE_BIG -> bigStarRadius
                STAR_SIZE_MEDIUM -> mediumStarRadius
                STAR_SIZE_SMALL -> smallStarRadius
                else -> throw IllegalArgumentException("Unsupported size")
            }

            val cx = rect.x + star.x * scale
            val cy = rect.y + star.y * scale
            val left = (cx - radius * scaleFactor).toInt()
            val top = (cy - radius * scaleFactor).toInt()
            val right = (cx + radius * scaleFactor).toInt()
            val bottom = (cy + radius * scaleFactor).toInt()

            val drawable = starDrawables[star.type]

            drawable.setBounds(left, top, right, bottom)
            drawable.draw(canvas)
        }

        graph.edges.forEach { (from, to) ->
            val start = graph.stars[from]
            val end = graph.stars[to]

            val sx = rect.x + start.x * scale
            val sy = rect.y + start.y * scale

            val fx = rect.x + end.x * scale
            val fy = rect.y + end.y * scale

            canvas.drawLine(sx, sy, fx, fy, zodiacSignPaint)
        }
    }

    private fun calculateZodiacRect(canvas: Canvas, viewState: ZodiacSignViewState): ZodiacRect {
        val aw = canvas.width // available width
        val ah = canvas.height // available height

        val notScaled = ah - marginTop - marginText - viewTextSize
        val size = notScaled * viewState.scale

        val y = marginTop + (1 - viewState.scale) * notScaled / 2
        val x = aw * viewState.bias - size / 2

        return ZodiacRect(x, y, size)
    }

    private class ZodiacRect(
        val x: Float,
        val y: Float,
        val size: Float
    )
}