package com.delizarov.starstell

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import java.lang.IllegalStateException

private const val AZIMUTH = 0
private const val PITCH = 1

typealias OnRotationChangedCallback = (Float, Float) -> Unit

class Compass(
    context: Context,
    private val callback: OnRotationChangedCallback
) : SensorEventListener {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        ?: throw IllegalStateException("no default accelerometer found")
    private val magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        ?: throw IllegalStateException("no default magnetic field sensor found")

    private val accelerometerReading = FloatArray(3)
    private val magnetometerReading = FloatArray(3)

    private val rotationMatrix = FloatArray(9)
    private val orientationAngles = FloatArray(3)

    fun startReadings() = registerListeners()

    fun stopReadings() = unregisterListeners()

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) { /* do nothing */
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, accelerometerReading, 0, accelerometerReading.size)
        } else if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, magnetometerReading, 0, magnetometerReading.size)
        }

        updateOrientationAngles()

        callback.invoke(orientationAngles[AZIMUTH], orientationAngles[PITCH])
    }

    private fun registerListeners() {
        listOf(accelerometer, magnetometer).forEach { sensor ->
            sensorManager.registerListener(
                this,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL,
                SensorManager.SENSOR_DELAY_UI
            )
        }
    }

    private fun updateOrientationAngles() {
        SensorManager.getRotationMatrix(
            rotationMatrix,
            null,
            accelerometerReading,
            magnetometerReading
        )

        SensorManager.getOrientation(rotationMatrix, orientationAngles)
    }

    private fun unregisterListeners() {
        sensorManager.unregisterListener(this)
    }
}