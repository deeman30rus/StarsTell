package com.delizarov.zodiacview

internal class Animation(
    private val duration: Long,
    fps: Int,
    private val listener: Listener
) {

    interface Listener {

        fun onDrawFrame(frameNo: Int)

        fun onFinish()
    }

    private val frameDuration = 1000 / fps

    var isRunning: Boolean = false

    // fixme here bug last frame problem

    fun start() {

        isRunning = true

        val startTime = System.currentTimeMillis()
        val endTime = startTime + duration

        var frameNo = 0

        while (isRunning) {
            val currentTime = System.currentTimeMillis()

            if (currentTime > endTime) {
                stop()
                continue
            }
            if (currentTime - startTime < (frameNo + 1) * frameDuration) continue

            listener.onDrawFrame(frameNo)
            ++frameNo
        }

        listener.onFinish()
    }

    fun stop() {
        isRunning = false
    }
}
