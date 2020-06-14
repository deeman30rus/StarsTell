package com.delizarov.parallaxview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.Choreographer
import android.view.View
import com.delizarov.core.assertion.assertTrue
import com.delizarov.parallaxview.physics.Vector

class ParallaxView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr) {

    private val gravitometer = Gravitometer(context, ::updateGravity)

    private val choreographer: Choreographer by lazy { Choreographer.getInstance() }

    private val frameRequester = FrameRequester()

    private val background = R.drawable.stars_bg1.bmp

    private var initialized = false

    private lateinit var vm: GravityViewModel

    private lateinit var renderer: Renderer

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        initialize()

        assertTrue(w < background.width) { "Background bitmap width should be wider than screen" }
        assertTrue(h < background.height) { "Background bitmap height should be higher than screen"}

        Log.v("measure", "screen $w $h")
        Log.v("measure", "bg ${background.width} ${background.height}")

        val ampX = (background.width - w) / 2
        val ampY = (background.height - h) / 2
        Log.v("measure", "amp $ampX $ampY")

        vm = GravityViewModel(ampX, ampY)
        renderer = Renderer(w, h, background)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        renderer.render(canvas ?: return)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        gravitometer.startReadings()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        gravitometer.stopReadings()
        initialized = false
    }

    private fun updateGravity(gravity: Vector) {
        vm.inclination = gravity
    }

    private fun initialize() {
        choreographer.postFrameCallback(frameRequester)

        initialized = true
    }

    private inner class FrameRequester : Choreographer.FrameCallback {

        override fun doFrame(frameTimeNanos: Long) {
            choreographer.postFrameCallback(frameRequester)

            vm.update()

            invalidate()
        }
    }

    private val Int.bmp: Bitmap
        get() = BitmapFactory.decodeResource(context.resources, this)

    private inner class Renderer(
        screenX: Int,
        screenY: Int,
        private val bg: Bitmap
    ) {

        val biasX = -(bg.width - screenX) / 2
        val biasY = -(bg.height - screenY) / 2

        private val matr = Matrix()

        fun render(canvas: Canvas) {

            Log.v("PView", "translate ${biasX + vm.dx} ${biasY + vm.dy}")

            matr.reset()
            matr.setTranslate(biasX + vm.dx, biasY + vm.dy)

            canvas.drawBitmap(bg, matr, null)
        }
    }
}
