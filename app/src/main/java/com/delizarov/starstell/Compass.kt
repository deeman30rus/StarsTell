package com.delizarov.starstell

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.opengl.Matrix
import java.lang.IllegalStateException

private const val AZIMUTH = 0
private const val PITCH = 1

typealias OnRotationChangedCallback = (FloatArray) -> Unit

class Compass(
    context: Context,
    private val callback: OnRotationChangedCallback
) : SensorEventListener {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private val rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)

    private val rotationMatrix = FloatArray(16)

    init {
        Matrix.setIdentityM(rotationMatrix, 0)
    }

    fun startReadings() = registerListeners()

    fun stopReadings() = unregisterListeners()

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) { /* do nothing */
    }

    override fun onSensorChanged(event: SensorEvent) {
        SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)
        callback.invoke(rotationMatrix)
    }

    private fun registerListeners() {
        sensorManager.registerListener(
            this,
            rotationSensor,
            SensorManager.SENSOR_DELAY_NORMAL,
            SensorManager.SENSOR_DELAY_UI
        )
    }

    private fun unregisterListeners() {
        sensorManager.unregisterListener(this)
    }
}