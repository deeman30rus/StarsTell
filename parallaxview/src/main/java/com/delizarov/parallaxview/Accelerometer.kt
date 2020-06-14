package com.delizarov.parallaxview

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.delizarov.parallaxview.physics.Vector

internal typealias OnAccelerationChangedCallback = (Vector) -> Unit
internal typealias AccelerationFilter = (Vector) -> Boolean

/**
 * Accelerometer sensor readings
 */
internal class Accelerometer(
    context: Context,
    private val callback: OnAccelerationChangedCallback,
    private val filter: AccelerationFilter = { true }
): SensorEventListener {

    private val sensorManager = context.sensorManager

    private val accelerometer = sensorManager.accelerometer

    fun startReadings() = registerListener()

    fun stopReadings() = unregisterListener()

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let { e ->
            val a = Vector(e.values[0], e.values[1])

            if (filter(a)) callback.invoke(a)
        }
    }

    private fun registerListener() {
        sensorManager.registerListener(
            this,
            accelerometer,
            SensorManager.SENSOR_DELAY_NORMAL,
            SensorManager.SENSOR_DELAY_UI
        )
    }

    private fun unregisterListener() {
        sensorManager.unregisterListener(this)
    }
}

private val Context.sensorManager: SensorManager
    get() = this.getSystemService(Context.SENSOR_SERVICE) as SensorManager

private val SensorManager.accelerometer: Sensor
    get() = this.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)

