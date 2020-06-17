package com.delizarov.testsite.fragments

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.opengl.Matrix
import android.os.Bundle
import com.delizarov.core.viewproperties.FragmentViewProperty
import com.delizarov.skybox.SkyBoxView
import com.delizarov.testsite.R

class SkyBoxFragment : BaseFragment(), SensorEventListener {

    private val skyboxView: SkyBoxView by FragmentViewProperty(
        R.id.skybox
    )

    private lateinit var sensorManager: SensorManager
    private lateinit var sensor: Sensor
    private var rotationMatrix = FloatArray(16)

    override val layoutRes: Int
        get() = R.layout.fragment_sky_box


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sensorManager = context!!.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
        Matrix.setIdentityM(rotationMatrix, 0)
    }


    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) { /* do nothing */ }

    override fun onSensorChanged(event: SensorEvent?) {
        SensorManager.getRotationMatrixFromVector(rotationMatrix, event!!.values)
        skyboxView.queueEvent {
            skyboxView.setRotationMatrix(rotationMatrix)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME)
    }
}