package com.delizarov.skybox

import android.app.ActivityManager
import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


class SkyBoxView(context: Context, attrs: AttributeSet?) :
    GLSurfaceView(context, attrs) {

    constructor(context: Context) : this(context, null)

    private val renderer = SkyBoxRenderer(context)

    init {
        setEGLContextClientVersion(2)
        setRenderer(renderer)
    }

    //todo выташить в extended метод
    val isSupported: Boolean
        get() {
            val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val configInfo = am.deviceConfigurationInfo

            return configInfo.reqGlEsVersion >= 0x20000
        }

    fun setRotationMatrix(matrix: FloatArray) {
        renderer.setRotationMatrix(matrix)
    }
}

private class SkyBoxRenderer(
    private val context: Context
) : GLSurfaceView.Renderer {

    private var texture: SkyboxTexture? = null

    private var width = 0
    private var height = 0

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES20.glClearColor(0f, 0f, 0f, 0f)

        texture = SkyboxTexture(context)
        texture?.prepare()
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)

        this.width = width
        this.height = height

        texture?.setViewSize(width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)

        texture?.drawSkyBox()
    }

    fun setRotationMatrix(matrix: FloatArray) {
        texture?.setRotationMatrix(matrix)
    }
}

