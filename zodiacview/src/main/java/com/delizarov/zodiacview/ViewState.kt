package com.delizarov.zodiacview

import android.content.res.Resources

private val biases = listOf(0f, 0.08f, 0.20f, 0.5f, 0.80f, 0.92f, 1f)
private val scales = listOf(0.2f, 0.34f, 0.48f, 0.92f, 0.48f, 0.34f, 0.2f)
private val alphas = listOf(0, 40, 110, 255, 110, 40, 0)

internal class ZodiacSignViewState(
    val sign: ZodiacSign,
    val bias: Float, // in percent from left side
    val scale: Float,
    val alpha: Int
)

internal sealed class ViewState {

    abstract val text: String

    abstract val textAlpha: Int

    abstract val zodiacs: List<ZodiacSignViewState>
}

internal class StaticViewState(
    resources: Resources,
    private val zodiacSign: ZodiacSign
) : ViewState() {

    override val text: String = resources.getString(zodiacSign.titleRes)

    override val textAlpha: Int = 255

    override val zodiacs: List<ZodiacSignViewState>
        get() {
            var sign = zodiacSign.prev().prev()

            return (1..5).map { i ->
                val vs = ZodiacSignViewState(sign, biases[i], scales[i], alphas[i])
                sign = sign.next()
                vs
            }.toList()
        }
}

private fun interpolate(start: Int, end: Int, total: Int, progress: Int) =
    start + ((end - start).toFloat() / total * progress).toInt()

private fun interpolate(start: Float, end: Float, total: Int, progress: Int) =
    start + (end - start) / total * progress

internal class AnimationViewState(
    private val resources: Resources,
    private val progress: Int,
    private val startSign: ZodiacSign,
    @TransitionDirection private val direction: Int
) : ViewState() {

    private val endSign = if (direction == DIRECTION_TO_NEXT) startSign.next() else startSign.prev()

    override val text: String
        get() {
            return resources.getString(if (progress < 50) startSign.titleRes else endSign.titleRes)
        }

    override val textAlpha: Int
        get() {
            return if (progress < 50) {
                interpolate(255, 0, 50, progress)
            } else {
                interpolate(0, 255, 50, progress - 50)
            }
        }

    override val zodiacs: List<ZodiacSignViewState>
        get() {
            return if (direction == DIRECTION_TO_NEXT) {
                var sign = startSign.prev().prev()
                (1..6).map { i ->
                    val vs = ZodiacSignViewState(
                        sign,
                        interpolate(biases[i], biases[i - 1], 100, progress),
                        interpolate(scales[i], scales[i - 1], 100, progress),
                        interpolate(alphas[i], alphas[i - 1], 100, progress)
                    )
                    sign = sign.next()
                    vs
                }.toList()
            } else {
                var sign = startSign.prev().prev().prev()
                (0..5).map { i ->
                    val vs = ZodiacSignViewState(
                        sign,
                        interpolate(biases[i], biases[i + 1], 100, progress),
                        interpolate(scales[i], scales[i + 1], 100, progress),
                        interpolate(alphas[i], alphas[i + 1], 100, progress)
                    )
                    sign = sign.next()
                    vs
                }.toList()
            }
        }
}
