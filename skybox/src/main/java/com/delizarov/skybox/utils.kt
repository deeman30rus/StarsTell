package com.delizarov.skybox

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.GLES20
import android.opengl.GLES30
import android.opengl.GLUtils
import android.util.Log
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

internal fun checkGLError(op: String) {
    val error = GLES30.glGetError()
    if (error != GLES30.GL_NO_ERROR) {
        val msg = "$op glError 0x ${Integer.toHexString(error)}"
        Log.e("delizarov", msg)
        throw RuntimeException(msg)
    }
}


internal fun Resources.loadCubeMap(res: IntArray): Int {
    val objectIds = IntArray(1)
    GLES20.glGenTextures(1, objectIds, 0)

    if (objectIds[0] == 0) {
        Log.v("delizarov", "Could not generate a new OpenGl texture object")
        return 0
    }

    val options = BitmapFactory.Options().apply {
        inScaled = false
    }

    val cubeBitmaps: Array<Bitmap?> = Array(6) { null }
    repeat(6) { i ->
        BitmapFactory.decodeResource(this, res[i], options)?.let { bmp ->
            cubeBitmaps[i] = bmp
        } ?: run {
            Log.v("delizarov", "could not load resource ${res[i]}")
            GLES20.glDeleteTextures(1, objectIds, 0)
            return 0
        }
    }

    GLES20.glBindTexture(GLES20.GL_TEXTURE_CUBE_MAP, objectIds[0])

    GLES20.glTexParameteri(
        GLES20.GL_TEXTURE_CUBE_MAP,
        GLES20.GL_TEXTURE_MIN_FILTER,
        GLES20.GL_LINEAR
    )
    GLES20.glTexParameteri(
        GLES20.GL_TEXTURE_CUBE_MAP,
        GLES20.GL_TEXTURE_MAG_FILTER,
        GLES20.GL_LINEAR
    )
    GLES20.glTexParameteri(
        GLES20.GL_TEXTURE_CUBE_MAP,
        GLES20.GL_TEXTURE_WRAP_S,
        GLES20.GL_CLAMP_TO_EDGE
    )
    GLES20.glTexParameteri(
        GLES20.GL_TEXTURE_CUBE_MAP,
        GLES20.GL_TEXTURE_WRAP_T,
        GLES20.GL_CLAMP_TO_EDGE
    )

    listOf(
        cubeBitmaps[0] to GLES20.GL_TEXTURE_CUBE_MAP_NEGATIVE_X,
        cubeBitmaps[1] to GLES20.GL_TEXTURE_CUBE_MAP_POSITIVE_X,
        cubeBitmaps[2] to GLES20.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y,
        cubeBitmaps[3] to GLES20.GL_TEXTURE_CUBE_MAP_POSITIVE_Y,
        cubeBitmaps[4] to GLES20.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z,
        cubeBitmaps[5] to GLES20.GL_TEXTURE_CUBE_MAP_POSITIVE_Z
    ).forEach { (bmp, target) ->
        GLUtils.texImage2D(target, 0, bmp, 0)
    }

    GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)

    cubeBitmaps.forEach { it!!.recycle() }

    return objectIds[0]
}

internal fun perspectiveM(
    m: FloatArray, yFovInDegrees: Float, aspect: Float,
    n: Float, f: Float
) {
    val angleInRadians = (yFovInDegrees * Math.PI / 180.0).toFloat()
    val a = (1.0 / Math.tan(angleInRadians / 2.0)).toFloat()
    m[0] = a / aspect
    m[1] = 0f
    m[2] = 0f
    m[3] = 0f

    m[4] = 0f
    m[5] = a
    m[6] = 0f
    m[7] = 0f

    m[8] = 0f
    m[9] = 0f
    m[10] = -((f + n) / (f - n))
    m[11] = -1f

    m[12] = 0f
    m[13] = 0f
    m[14] = -(2f * f * n / (f - n))
    m[15] = 0f
}

internal fun createByteBuffer(array: ByteArray): ByteBuffer {
    val buffer = ByteBuffer.allocate(array.size)
    buffer.order(ByteOrder.nativeOrder())
    buffer.put(array)
    buffer.position(0)
    return buffer
}

internal fun createFloatBuffer(array: FloatArray): FloatBuffer {
    val sizeOfFloat = 4
    val bb = ByteBuffer.allocateDirect(array.size * sizeOfFloat)
    bb.order(ByteOrder.nativeOrder())
    val fb = bb.asFloatBuffer()
    fb.put(array)
    fb.position(0)
    return fb
}
