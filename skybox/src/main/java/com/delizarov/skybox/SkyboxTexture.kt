package com.delizarov.skybox

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLES30
import android.opengl.Matrix
import android.util.Log

private const val COORDS_PER_VERTEX = 3

private val CUBE_COORDINATES = floatArrayOf(
    -1f, 1f, 1f,     // 0 - top left near
    1f, 1f, 1f,     // 1 - top right near
    -1f, -1f, 1f,     // 2 - bottom left near
    1f, -1f, 1f,     // 3 - bottom right near
    -1f, 1f, -1f,    // 4 - top left far
    1f, 1f, -1f,    // 5 top right far
    -1f, -1f, -1f,    // 6 bottom left far
    1f, -1f, -1f     // 7 bottom right far
)

private val CUBE_INDICES = byteArrayOf(
    // front
    1, 3, 0,
    0, 3, 2,

    // back
    4, 6, 5,
    5, 6, 7,

    //left
    0, 2, 4,
    4, 2, 6,

    //right
    5, 7, 1,
    1, 7, 3,

    //top
    5, 1, 4,
    4, 1, 0,

    //bottom
    6, 2, 7,
    7, 2, 3
)

class SkyboxTexture(
    private val context: Context
) {

    private val vertexBuffer = createFloatBuffer(CUBE_COORDINATES)
    private val indexBuffer = createByteBuffer(CUBE_INDICES)

    private var rotationMatrix = FloatArray(16)
    private val viewMatrix = FloatArray(16)
    private val projectionMatrix = FloatArray(16)
    private val mvPMatrix = FloatArray(16)

    private var programId = 0

    private var matrixHandle = 0
    private var textureUnitHandle = 0
    private var positionHandle = 0

    private var skyboxTexture = 0

    private var width = 0
    private var height = 0

    init {
        Matrix.setIdentityM(rotationMatrix, 0)
        Matrix.setIdentityM(viewMatrix, 0)
        Matrix.setIdentityM(projectionMatrix, 0)
        Matrix.setIdentityM(mvPMatrix, 0)
    }

    fun prepare() {
        programId = createProgramId(
            context.resources.getString(R.string.vertex_skybox),
            context.resources.getString(R.string.fragment_skybox)
        )
        matrixHandle = GLES20.glGetUniformLocation(programId, "u_Matrix")
        textureUnitHandle = GLES20.glGetUniformLocation(programId, "u_TextureUnit")
        positionHandle = GLES20.glGetAttribLocation(programId, "a_Position")
        skyboxTexture = context.resources.loadCubeMap(
            intArrayOf(
                R.drawable.nebula_lf, R.drawable.nebula_rt,
                R.drawable.nebula_dn, R.drawable.nebula_up,
                R.drawable.nebula_ft, R.drawable.nebula_bk
            )
        )
    }

    fun setViewSize(width: Int, height: Int) {
        this.width = width
        this.height = height

        Matrix.setLookAtM(
            viewMatrix, 0,
            0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, -1.0f,
            0f, 1.0f, 0.0f
        )

        val ratio = width.toFloat() / height.toFloat()
        perspectiveM(projectionMatrix, 45f, ratio, 1f, 300f)
    }

    fun drawSkyBox() {
        GLES20.glUseProgram(programId)
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_CUBE_MAP, skyboxTexture)
        calculateMatrix()
        GLES20.glUniformMatrix4fv(matrixHandle, 1, false, mvPMatrix, 0)
        GLES20.glUniform1i(textureUnitHandle, 0)
        GLES20.glEnableVertexAttribArray(positionHandle)
        GLES20.glVertexAttribPointer(
            positionHandle, COORDS_PER_VERTEX,
            GLES20.GL_FLOAT, false, 0, vertexBuffer
        )
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, 36, GLES20.GL_UNSIGNED_BYTE, indexBuffer)
        GLES20.glUseProgram(0)
    }

    private fun calculateMatrix() {
        Matrix.setIdentityM(viewMatrix, 0)
        Matrix.setLookAtM(
            viewMatrix, 0,
            0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, -1.0f,
            0f, 1.0f, 0.0f
        )
        Matrix.multiplyMM(viewMatrix, 0, viewMatrix, 0, rotationMatrix, 0)
        Matrix.rotateM(viewMatrix, 0, 90f, 1f, 0f, 0f)
        Matrix.multiplyMM(mvPMatrix, 0, projectionMatrix, 0, viewMatrix, 0)
    }

    fun setRotationMatrix(matrix: FloatArray) {
        rotationMatrix = matrix
    }

    private fun createProgramId(vertexSource: String, fragmentSource: String): Int {
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
}


