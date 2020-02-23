package com.delizarov.parallaxview

import android.content.Context
import android.graphics.BitmapFactory
import android.opengl.GLES20
import android.opengl.GLES20.*
import android.opengl.GLUtils

private fun vector() = FloatArray(4)
private fun matrix() = FloatArray(16)

class DeepSpaceTexture(
    private val context: Context
) {

    private var width = 0
    private var height = 0

    private var programId = 0

    private var positionLocation = 0
    private var textureLocation = 0
    private var textureUnitLocation = 0
    private var matrixLocation = 0

    private var textureId = 0

    fun prepare() {
        programId = createProgramId(
            context.resources.getString(R.string.pv_background_vertex),
            context.resources.getString(R.string.pv_backfround_fragment)
        )

        positionLocation = glGetAttribLocation(programId, "a_Position")
        textureLocation = glGetAttribLocation(programId, "a_Texture")
        textureUnitLocation = glGetAttribLocation(programId, "u_TextureUnit")
        matrixLocation = glGetAttribLocation(programId, "u_Matrix")

        val objId = IntArray(1)
        glGenTextures(1, objId, 0)

        textureId = objId[0]

        glPixelStorei(GL_UNPACK_ALIGNMENT, 1)
        glBindTexture(GL_TEXTURE_2D, textureId)

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)


        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT)

        val bmp = BitmapFactory.decodeResource(context.resources, R.drawable.bg)

        GLUtils.texImage2D(GL_TEXTURE_2D, 0, bmp, 0)

        bmp.recycle()

        glGenerateMipmap(GL_TEXTURE_2D)
    }

    fun setSize() {
        this.width = width
        this.height = height

        val ratio = width.toFloat() / height.toFloat()
        MatrixHelper.perspectiveM(projectionMatr, 45f, ratio, 1f, 300f)
    }

    fun draw() {

    }


}