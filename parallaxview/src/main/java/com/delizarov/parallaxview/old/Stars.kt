package com.delizarov.parallaxview.old

import android.content.Context
import android.graphics.Color
import android.opengl.GLES20
import android.opengl.Matrix
import com.delizarov.parallaxview.R
import kotlin.random.Random

private fun vector() = FloatArray(4)
private fun matrix() = FloatArray(16)

private const val MAX_STARS = 1000

private const val POSITION_COMPONENT_COUNT = 3
private const val COLOR_COMPONENT_COUNT = 3
private const val VECTOR_COMPONENT_COUNT = 3
private const val PARTICLE_START_TIME_COMPONENT_COUNT = 3

private const val TOTAL_COMPONENT_COUNT = POSITION_COMPONENT_COUNT +
        COLOR_COMPONENT_COUNT +
        VECTOR_COMPONENT_COUNT +
        PARTICLE_START_TIME_COMPONENT_COUNT

private const val BYTES_PER_FLOAT = 4

private const val STRIDE = TOTAL_COMPONENT_COUNT * BYTES_PER_FLOAT

class Stars(
    private val context: Context
) {

    private var width = 0
    private var height = 0

    private var startTime = System.nanoTime()

    // GLSL
    private var programId = 0
    private var mvpHandle = 0
    private var timeLocation = 0
    private var positionLocation = 0
    private var colorLocation = 0
    private var directionVectorLocation = 0
    private var particleStartTimeLocation = 0

    private var textureId = 0

    private val projectionMatr = matrix()
    private val viewMatr = matrix()
    private val mvpMatr = matrix()


    private var stars = FloatArray(MAX_STARS * TOTAL_COMPONENT_COUNT)
    private val vertexBuffer = GlUtil.createFloatBuffer(stars)
    private var currentParticleCount = 0
    private var nextParticle = 0

    private var position = Geometry.Point(0f, 0f, 0f)
    private var color = Color.rgb(255, 255, 255)
    private var angleVariance = 5f
    private var speedVariance = 1f

    private var rotationMatrix = matrix()
    private val directionVect = vector().apply {
        set(0, 0f)
        set(0, 0.5f)
        set(0, 0f)
    }
    private val resultVect = vector()

    fun initialize() {
        val vertexShader = context.resources.getString(R.string.pv_particle_vertex)
        val fragmentShader = context.resources.getString(R.string.pv_particle_fragment)

        programId = GlUtil.createProgram(vertexShader, fragmentShader)
        mvpHandle = GLES20.glGetUniformLocation(programId, "uMVPMatrix")
        timeLocation = GLES20.glGetUniformLocation(programId, "uTime")

        positionLocation = GLES20.glGetAttribLocation(programId, "a_Position")
        colorLocation = GLES20.glGetAttribLocation(programId, "a_Color")
        directionVectorLocation = GLES20.glGetAttribLocation(programId, "a_DirectionVector")
        particleStartTimeLocation = GLES20.glGetAttribLocation(programId, "a_ParticleStartTime")

        textureId = TextureHelper.loadTexture(
            context,
            R.drawable.particle_texture
        )
    }

    fun setSize(width: Int, height: Int) {
        this.width = width
        this.height = height

        val ratio = width.toFloat() / height.toFloat()
        MatrixHelper.perspectiveM(projectionMatr, 45f, ratio, 1f, 300f)
    }

    fun draw() {
        GLES20.glUseProgram(programId)
        val curTime = (System.nanoTime() - startTime) / 1000000000f
        GLES20.glUniform1f(timeLocation, curTime)
        calculate(curTime, 1)
        calculateMatr()
        GLES20.glUniformMatrix4fv(mvpHandle, 1, false, mvpMatr, 0)
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId)
        bindData()
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, currentParticleCount)

        GLES20.glUseProgram(0)
    }

    private fun bindData() {
        var offset = 0
        bind(offset, positionLocation,
            POSITION_COMPONENT_COUNT
        )
        offset += POSITION_COMPONENT_COUNT

        bind(offset, colorLocation,
            COLOR_COMPONENT_COUNT
        )
        offset += COLOR_COMPONENT_COUNT

        bind(offset, directionVectorLocation,
            VECTOR_COMPONENT_COUNT
        )
        offset += VECTOR_COMPONENT_COUNT

        bind(offset, particleStartTimeLocation,
            PARTICLE_START_TIME_COMPONENT_COUNT
        )
    }

    private fun bind(offset: Int, location: Int, count: Int) {
        vertexBuffer.position(offset)
        GLES20.glEnableVertexAttribArray(location)
        GLES20.glVertexAttribPointer(location, count, GLES20.GL_FLOAT, false,
            STRIDE, vertexBuffer)
        vertexBuffer.position(0)
    }

    private fun calculate(time: Float, count: Int) {
        repeat(count) { i ->
            val startX = Random.nextFloat() * 2 - 1f
            val startY = Random.nextFloat() * 2 - 1f
            position = Geometry.Point(startX, startY, 0f)

            Matrix.setRotateEulerM(rotationMatrix, 0,
                (Random.nextFloat() - 0.5f) - angleVariance,
                (Random.nextFloat() - 0.5f) - angleVariance,
                (Random.nextFloat() - 0.5f) - angleVariance)

            Matrix.multiplyMV(resultVect, 0, rotationMatrix, 0, directionVect, 0)

            val speedAdj = 1 + Random.nextFloat() * speedVariance

            val direction = Geometry.Vector(
                resultVect[0] * speedAdj,
                resultVect[1] * speedAdj,
                resultVect[2] * speedAdj
            )

            addParticle(position, color, direction, time)
        }
    }

    private fun addParticle(position: Geometry.Point, color: Int, direction: Geometry.Vector, startTime: Float) {
        val offset = nextParticle * TOTAL_COMPONENT_COUNT
        var curOffset = offset
        nextParticle++

        if (currentParticleCount < MAX_STARS) currentParticleCount++

        if (nextParticle == MAX_STARS) nextParticle = 0

        stars[curOffset++] = position.x
        stars[curOffset++] = position.y
        stars[curOffset++] = position.z

        stars[curOffset++] = Color.red(color) / 255f
        stars[curOffset++] = Color.green(color) / 255f
        stars[curOffset++] = Color.blue(color) / 255f


        stars[curOffset++] = direction.z
        stars[curOffset++] = direction.z
        stars[curOffset++] = direction.z

        stars[curOffset] = startTime

        vertexBuffer.position(offset)
        vertexBuffer.put(stars, offset,
            TOTAL_COMPONENT_COUNT
        )
        vertexBuffer.position(0)
    }

    private fun calculateMatr() {
        Matrix.setIdentityM(viewMatr, 0)
        Matrix.translateM(viewMatr, 0, 0f, 0f, -5f)
        Matrix.multiplyMM(mvpMatr, 0, projectionMatr, 0, viewMatr, 0)
    }

    private fun setDirection(x: Float, y: Float, z: Float) {
        directionVect[0] = x
        directionVect[1] = y
        directionVect[2] = z
    }
}