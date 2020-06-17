package com.example.forecast.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import com.delizarov.core.fragments.BaseFragment
import com.delizarov.core.viewproperties.FragmentViewProperty
import com.delizarov.zodiacview.ZodiacView
import com.example.forecast.R
import kotlin.math.abs

private const val VELOCITY_THRESHOLD = 10

class ForecastFragment : BaseFragment() {

    private lateinit var gestureDetector: GestureDetector
    private val zodiacView: ZodiacView by FragmentViewProperty(this, R.id.zodiac_view)

    override val layoutRes: Int
        get() = R.layout.fragment_forecaset

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gestureDetector = GestureDetector(activity, GestureListener())

        view.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
        }
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
            Log.v("Forecast", "on fling")

            if (abs(velocityX) < VELOCITY_THRESHOLD) return false

            if (velocityX < 0) {
                showNext()
            } else {
                showPrev()
            }

            return true
        }

        override fun onDown(e: MotionEvent?) = true
    }
}