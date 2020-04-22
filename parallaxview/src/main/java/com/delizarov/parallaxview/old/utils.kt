package com.delizarov.parallaxview.old

import android.opengl.GLES30
import android.util.Log

private fun checkGLError(op: String) {
    val error = GLES30.glGetError()
    if (error != GLES30.GL_NO_ERROR) {
        val msg = "$op glError 0x ${Integer.toHexString(error)}"
        Log.e("delizarov", msg)
        throw RuntimeException(msg)
    }
}

internal fun createProgramId(vertexSource: String, fragmentSource: String): Int {
    fun loadShader(type: Int, source: String): Int {
        val shader = GLES30.glCreateShader(type)
        checkGLError("glCreateShader $type")
        GLES30.glShaderSource(shader, source)
        GLES30.glCompileShader(shader)
        val status = IntArray(1)
        GLES30.glGetShaderiv(shader, GLES30.GL_COMPILE_STATUS, status, 0)
        if (status[0] == 0) {
            Log.e("delizarov", "could not compile shader $type")
            Log.e("delizarov", GLES30.glGetShaderInfoLog(shader))
            GLES30.glDeleteShader(shader)
            return 0
        }

        return shader
    }

    val vertexShader = loadShader(GLES30.GL_VERTEX_SHADER, vertexSource)
    if (vertexShader == 0) {
        return 0
    }

    val pixelShader = loadShader(GLES30.GL_FRAGMENT_SHADER, fragmentSource)
    if (pixelShader == 0) {
        return 0
    }

    val programId = GLES30.glCreateProgram()
    checkGLError("glCreateProgram")
    if (programId == 0) {
        Log.e("delizarov", "can't create program")
    }

    GLES30.glAttachShader(programId, vertexShader)
    checkGLError("glAttachShader")

    GLES30.glAttachShader(programId, pixelShader)
    checkGLError("glAttachShader")

    GLES30.glLinkProgram(programId)
    val linkStatus = IntArray(1)
    GLES30.glGetProgramiv(programId, GLES30.GL_LINK_STATUS, linkStatus, 0)

    if (linkStatus[0] != GLES30.GL_TRUE) {
        Log.e("delizaarov", "Could not link programm")
        Log.e("delizaarov", GLES30.glGetProgramInfoLog(programId))
        GLES30.glDeleteProgram(programId)

        return 0
    }

    return programId
}
