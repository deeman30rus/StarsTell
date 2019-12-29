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

    init {
        setEGLContextClientVersion(2)
        setRenderer(SkyTubeRenderer())
    }

    val isSupported: Boolean
        get() {
            val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val configInfo = am.deviceConfigurationInfo

            return configInfo.reqGlEsVersion >= 0x20000
        }
}

class SkyTubeRenderer : GLSurfaceView.Renderer {
    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES20.glClearColor(0.056f, 0.15f, 0.4f, 0f)
    }
}