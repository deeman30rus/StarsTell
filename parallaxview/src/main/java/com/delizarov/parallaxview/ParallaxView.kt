package com.delizarov.parallaxview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.Choreographer
import android.view.View
import com.delizarov.core.assertion.assertTrue
import com.delizarov.parallaxview.physics.Vector
import com.delizarov.parallaxview.viewmodels.GravityViewModel
import com.delizarov.parallaxview.viewmodels.Star
import com.delizarov.parallaxview.viewmodels.StarsViewModel

private const val STARS_COUNT = 50

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

    private lateinit var gravityVM: GravityViewModel
    private lateinit var starsVM: StarsViewModel

    private val layers = mutableListOf<Layer>()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        initialize()

        assertTrue(w < background.width) { "Background bitmap width should be wider than screen" }
        assertTrue(h < background.height) { "Background bitmap height should be higher than screen" }

        val xMoveArea = (background.width - w) / 2
        val yMoveArea = (background.height - h) / 2

        gravityVM = GravityViewModel(xMoveArea, yMoveArea)
        val area = Rect(0, 0, xMoveArea + w, yMoveArea + h)

        starsVM = StarsViewModel {
            (0 until STARS_COUNT).map { Star.random(area) }
        }

        with(layers) {
            add(BackgroundLayer(gravityVM, background, -xMoveArea, -yMoveArea))
            add(StarsLayer(gravityVM, starsVM, -xMoveArea / 2, -yMoveArea / 2))
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.draw(layers)
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
        gravityVM.inclination = gravity
    }

    private fun initialize() {
        choreographer.postFrameCallback(frameRequester)

        initialized = true
    }

    private inner class FrameRequester : Choreographer.FrameCallback {

        override fun doFrame(frameTimeNanos: Long) {
            choreographer.postFrameCallback(frameRequester)

            gravityVM.update()
            starsVM.update()

            invalidate()
        }
    }

    private val Int.bmp: Bitmap
        get() = BitmapFactory.decodeResource(context.resources, this)
}

private fun Canvas.draw(layers: List<Layer>) = layers.forEach { it.drawOn(this) }

private sealed class Layer(protected val vm: GravityViewModel) {

    abstract fun drawOn(canvas: Canvas)
}

private class BackgroundLayer(
    vm: GravityViewModel,
    private val bg: Bitmap,
    private val left: Int,
    private val top: Int
) : Layer(vm) {

    private val matrix = Matrix()

    override fun drawOn(canvas: Canvas) {
        matrix.reset()
        matrix.setTranslate(left + vm.dx, top + vm.dy)

        canvas.drawBitmap(bg, matrix, null)
    }
}

private class StarsLayer(
    gravity: GravityViewModel,
    private val starsVM: StarsViewModel,
    private val left: Int,
    private val top: Int
) : Layer(gravity) {

    private val paint = Paint().apply {
        style = Paint.Style.FILL
    }

    override fun drawOn(canvas: Canvas) {
        var x: Float
        var y: Float

        starsVM.stars.forEach { star ->
            val alpha = (255 * star.alpha).toInt()
            paint.color = Color.argb(alpha, 255, 255, 255)

            x = star.x + left + vm.dx * 0.2f
            y = star.y + top + vm.dy * 0.2f
            canvas.drawCircle(x, y, 10f, paint)
        }
    }
}
