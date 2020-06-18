package com.example.forecast.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import com.delizarov.core.fragments.BaseFragment
import com.delizarov.core.viewproperties.FragmentViewProperty
import com.delizarov.zodiacview.ZodiacSign
import com.delizarov.zodiacview.ZodiacView
import com.example.forecast.R
import com.example.forecast.domain.Forecast
import com.example.forecast.mvp.ForecastFeature
import com.example.forecast.mvp.ForecastPresenter
import com.example.forecast.mvp.ForecastView
import kotlin.math.abs

private const val VELOCITY_THRESHOLD = 10

class ForecastFragment : BaseFragment(), ForecastView {

    private lateinit var gestureDetector: GestureDetector
    private val zodiacView: ZodiacView by FragmentViewProperty(this, R.id.zodiac_view)

    private lateinit var forecastFeature: ForecastFeature
    private val forecastPresenter: ForecastPresenter
        get() = forecastFeature.presenter

    override val sign: ZodiacSign
        get() = zodiacView.sign

    override val layoutRes: Int
        get() = R.layout.fragment_forecaset

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        forecastFeature = ForecastFeature(
            ForecastPresenter(),
            this
        )

        forecastFeature.insertInto(this)
        forecastFeature.enable()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gestureDetector = GestureDetector(activity, GestureListener())

        view.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
        }

        zodiacView.setOnSignChanged { sign ->
            forecastPresenter.onSignSelected(sign)
        }

        forecastPresenter.onSignSelected(sign)
    }

    override fun showForecast(forecast: Forecast) {
        super.showForecast(forecast)

        Log.v("forecast", forecast.text)
    }

    private fun swipeLeft() {
        zodiacView.nextSign()
    }

    private fun swipeRight() {
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
                swipeLeft()
            } else {
                swipeRight()
            }

            return true
        }

        override fun onDown(e: MotionEvent?) = true
    }
}