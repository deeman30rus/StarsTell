package com.delizarov.starstell

import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.rotationMatrix
import com.delizarov.core.ActivityViewProperty
import com.delizarov.skybox.SkyBoxView

import com.delizarov.zodiacview.ZodiacView
import kotlin.math.abs

private const val VELOCITY_THRESHOLD = 10

class MainActivity : AppCompatActivity() {

    private val skyBoxView: SkyBoxView by ActivityViewProperty(R.id.skybox)
    private val zodiacView: ZodiacView by ActivityViewProperty(R.id.zodiac_view)

    private lateinit var compass: Compass

    private lateinit var gestureDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gestureDetector = GestureDetector(this, GestureListener())

        setContentView(R.layout.activity_main)

        if (!skyBoxView.isSupported) {
            Toast.makeText(this, "Not supported", Toast.LENGTH_SHORT).show()
            return
        }

        compass = Compass(this) { matrix ->
            skyBoxView.queueEvent {
                skyBoxView.setRotationMatrix(matrix)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        compass.startReadings()
    }

    override fun onPause() {
        super.onPause()
        compass.stopReadings()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event)
    }

    private fun showNext() {
        zodiacView.nextSign()
    }

    private fun showPrev() {
        zodiacView.prevSign()
    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {

            if (abs(velocityX) < VELOCITY_THRESHOLD) return false

            if (velocityX < 0) {
                showNext()
            } else {
                showPrev()
            }

            return true
        }
    }
}
