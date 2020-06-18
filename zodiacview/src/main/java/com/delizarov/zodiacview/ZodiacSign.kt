package com.delizarov.zodiacview

import androidx.annotation.StringRes

enum class ZodiacSign(
    @StringRes var titleRes: Int
) {

    Aries(R.string.aries),
    Taurus(R.string.taurus),
    Gemini(R.string.gemini),
    Cancer(R.string.cancer),
    Leo(R.string.leo),
    Virgo(R.string.virgo),
    Libra(R.string.libra),
    Scorpio(R.string.scorpio),
    Ophiuchus(R.string.ophiuchus),
    Sagittarius(R.string.sagittarius),
    Capricorn(R.string.capricorn),
    Aquarius(R.string.aquarius),
    Pisces(R.string.pisces);

    fun next(): ZodiacSign = sign(this.ordinal + 1)

    fun prev(): ZodiacSign = sign(this.ordinal - 1)

    private fun sign(pos: Int) = values()[(pos + values().size) % values().size]

}
