package com.delizarov.parallaxview

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import com.delizarov.parallaxview.old.ParticleFilter
import com.delizarov.parallaxview.old.Stars
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


class ParallaxView1(context: Context, attrs: AttributeSet?) : GLSurfaceView(context, attrs) {

    constructor(context: Context) : this (context, null)

    private val renderer = Renderer(context)

    init {
        setEGLContextClientVersion(2)
        setRenderer(renderer)
    }
}

private class Renderer(
    private val context: Context
) : GLSurfaceView.Renderer {

    private var width = 0
    private var height = 0

    private var filter: ParticleFilter? = null
    private var stars: Stars? = null



    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES20.glClearColor(0f, 0f, 0f, 0f)

//        filter = ParticleFilter(context)
//        filter?.createProgram()

        stars = Stars(context)
        stars?.initialize()
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)

        this.width = width
        this.height = height

//        filter?.setViewSize(width, height)
        stars?.setSize(width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)

        drawParticles()
    }

    private fun drawParticles() {
        GLES20.glEnable(GLES20.GL_BLEND)
        GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE)
//        filter?.drawParticle()
        stars?.draw()
        GLES20.glDisable(GLES20.GL_BLEND)
    }
}
