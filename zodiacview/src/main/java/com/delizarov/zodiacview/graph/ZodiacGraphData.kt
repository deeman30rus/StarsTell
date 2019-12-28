package com.delizarov.zodiacview.graph

import com.delizarov.zodiacview.R
import com.delizarov.zodiacview.ZodiacSign
import com.delizarov.zodiacview.graph.ZodiacGraph.Companion.zodiac
import java.lang.IllegalArgumentException

internal val starDrawableRes = listOf(
    R.drawable.zodiac_view_star2,
    R.drawable.zodiac_view_star2_45cw
)

private fun graphs(vararg graphs: ZodiacGraph) = graphs.associateBy { it.sign }

private val graphs = graphs(
    zodiac {
        sign = ZodiacSign.Pisces
        stars = stars(
            star(14,90, STAR_SIZE_SMALL),
            star(25,94, STAR_SIZE_MEDIUM),
            star(30,84, STAR_SIZE_BIG),
            star(55,84, STAR_SIZE_MEDIUM),
            star(72,86, STAR_SIZE_MEDIUM),
            star(92,88, STAR_SIZE_BIG),
            star(80,80, STAR_SIZE_MEDIUM),
            star(66,66, STAR_SIZE_BIG),
            star(61,58, STAR_SIZE_MEDIUM),
            star(50,34, STAR_SIZE_MEDIUM),
            star(48,22, STAR_SIZE_SMALL),
            star(41,17, STAR_SIZE_SMALL),
            star(43,10, STAR_SIZE_SMALL),
            star(48,8, STAR_SIZE_SMALL),
            star(58,9, STAR_SIZE_SMALL),
            star(58,19, STAR_SIZE_SMALL),
            star(54,24, STAR_SIZE_SMALL)
        )
        edges = edges(
            0 to 1,
            1 to 2,
            2 to 3,
            3 to 4,
            4 to 5,
            5 to 6,
            6 to 7,
            7 to 8,
            8 to 9,
            9 to 10,
            10 to 11,
            11 to 12,
            12 to 13,
            13 to 14,
            14 to 15,
            15 to 16,
            16 to 10
        )
    },
    zodiac {
        sign = ZodiacSign.Aries
        stars = stars(
            star(26, 40, STAR_SIZE_MEDIUM),
            star(57, 46, STAR_SIZE_BIG),
            star(70, 53, STAR_SIZE_MEDIUM),
            star(72, 59, STAR_SIZE_SMALL)
        )
        edges = edges(
            0 to 1,
            1 to 2,
            2 to 3
        )
    },
    zodiac {
        sign = ZodiacSign.Taurus
        stars = stars(
            star(18, 26, STAR_SIZE_BIG),
            star(44, 49, STAR_SIZE_MEDIUM),
            star(49, 52, STAR_SIZE_SMALL),
            star(54, 55, STAR_SIZE_SMALL),
            star(64, 64, STAR_SIZE_BIG),
            star(84, 78, STAR_SIZE_MEDIUM),
            star(88, 82, STAR_SIZE_SMALL),
            star(53, 49, STAR_SIZE_SMALL),
            star(50, 43, STAR_SIZE_SMALL),
            star(47, 30, STAR_SIZE_MEDIUM),
            star(32, 10, STAR_SIZE_BIG)
        )
        edges = edges(
            0 to 1,
            1 to 2,
            2 to 3,
            3 to 4,
            3 to 7,
            4 to 5,
            5 to 6,
            7 to 8,
            8 to 9,
            9 to 10
        )
    },
    zodiac {
        sign = ZodiacSign.Gemini
        stars = stars(
            star(20, 23, STAR_SIZE_BIG),
            star(26, 23, STAR_SIZE_MEDIUM),
            star(34, 24, STAR_SIZE_SMALL),
            star(61, 38, STAR_SIZE_MEDIUM),
            star(76, 43, STAR_SIZE_MEDIUM),
            star(82, 41, STAR_SIZE_SMALL),
            star(72, 49, STAR_SIZE_SMALL),
            star(66, 61, STAR_SIZE_BIG),
            star(62, 73, STAR_SIZE_MEDIUM),
            star(42, 52, STAR_SIZE_MEDIUM),
            star(29, 49, STAR_SIZE_MEDIUM),
            star(13, 42, STAR_SIZE_SMALL),
            star(11, 35, STAR_SIZE_MEDIUM)
        )
        edges = edges(
            0 to 1,
            1 to 2,
            2 to 3,
            3 to 4,
            4 to 5,
            4 to 6,
            6 to 7,
            7 to 8,
            7 to 9,
            9 to 10,
            10 to 11,
            11 to 12,
            12 to 0
        )
    },
    zodiac {
        sign = ZodiacSign.Cancer
        stars = stars(
            star(31, 17, STAR_SIZE_BIG),
            star(43, 33, STAR_SIZE_MEDIUM),
            star(46, 40, STAR_SIZE_MEDIUM),
            star(45, 64, STAR_SIZE_BIG),
            star(78, 54, STAR_SIZE_BIG)
        )
        edges = edges(
            0 to 1,
            1 to 2,
            2 to 3,
            2 to 4
        )
    },
    zodiac {
        sign = ZodiacSign.Leo
        stars = stars(
            star(57, 14, STAR_SIZE_SMALL),
            star(48, 14, STAR_SIZE_MEDIUM),
            star(48, 31, STAR_SIZE_SMALL),
            star(55, 39, STAR_SIZE_MEDIUM),
            star(65, 37, STAR_SIZE_SMALL),
            star(75, 45, STAR_SIZE_BIG),
            star(41, 75, STAR_SIZE_MEDIUM),
            star(29, 89, STAR_SIZE_BIG),
            star(32, 64, STAR_SIZE_MEDIUM)
        )
        edges = edges(
            0 to 1,
            1 to 2,
            2 to 3,
            3 to 4,
            3 to 8,
            4 to 5,
            5 to 6,
            6 to 7,
            7 to 8
        )
    },
    zodiac {
        sign = ZodiacSign.Virgo
        stars = stars(
            star(66, 7, STAR_SIZE_SMALL),
            star(65, 25, STAR_SIZE_MEDIUM),
            star(61, 37, STAR_SIZE_MEDIUM),
            star(63, 56, STAR_SIZE_MEDIUM),
            star(71, 69, STAR_SIZE_MEDIUM),
            star(58, 87, STAR_SIZE_MEDIUM),
            star(53, 86, STAR_SIZE_MEDIUM),
            star(46, 98, STAR_SIZE_SMALL),
            star(27, 90, STAR_SIZE_SMALL),
            star(38, 70, STAR_SIZE_MEDIUM),
            star(48, 61, STAR_SIZE_BIG),
            star(49, 40, STAR_SIZE_BIG),
            star(31, 34, STAR_SIZE_MEDIUM)
        )
        edges = edges(
            0 to 1,
            1 to 2,
            2 to 3,
            2 to 11,
            3 to 4,
            4 to 5,
            4 to 10,
            5 to 6,
            6 to 7,
            8 to 9,
            9 to 10,
            10 to 11,
            11 to 12
        )
    },
    zodiac {
        sign = ZodiacSign.Libra
        stars = stars(
            star(26, 46, STAR_SIZE_SMALL),
            star(35, 41, STAR_SIZE_MEDIUM),
            star(42, 35, STAR_SIZE_MEDIUM),
            star(51, 16, STAR_SIZE_BIG),
            star(75, 32, STAR_SIZE_BIG),
            star(71, 66, STAR_SIZE_BIG),
            star(53, 79, STAR_SIZE_MEDIUM),
            star(52, 85, STAR_SIZE_SMALL)
        )
        edges = edges(
            0 to 1,
            1 to 2,
            2 to 3,
            3 to 4,
            3 to 5,
            4 to 5,
            5 to 6,
            6 to 7
        )
    },
    zodiac {
        sign = ZodiacSign.Scorpio
        stars = stars(
            star(16, 63, STAR_SIZE_SMALL),
            star(9, 70, STAR_SIZE_MEDIUM),
            star(7, 76, STAR_SIZE_BIG),
            star(14, 86, STAR_SIZE_MEDIUM),
            star(30, 87, STAR_SIZE_BIG),
            star(42, 82, STAR_SIZE_MEDIUM),
            star(46, 70, STAR_SIZE_MEDIUM),
            star(48, 57, STAR_SIZE_MEDIUM),
            star(61, 38, STAR_SIZE_SMALL),
            star(67, 35, STAR_SIZE_SMALL),
            star(72, 31, STAR_SIZE_SMALL),
            star(91, 25, STAR_SIZE_BIG),
            star(87, 15, STAR_SIZE_MEDIUM),
            star(90, 34, STAR_SIZE_MEDIUM),
            star(89, 45, STAR_SIZE_SMALL)
        )
        edges = edges(
            0 to 1,
            1 to 2,
            2 to 3,
            3 to 4,
            4 to 5,
            5 to 6,
            6 to 7,
            7 to 8,
            8 to 9,
            9 to 10,
            10 to 11,
            11 to 12,
            11 to 13,
            13 to 14
        )
    },
    zodiac {
        sign = ZodiacSign.Sagittarius
        stars = stars(
            star(21, 7, STAR_SIZE_SMALL),
            star(33, 15, STAR_SIZE_SMALL),
            star(37, 15, STAR_SIZE_MEDIUM),
            star(43, 12, STAR_SIZE_SMALL),
            star(46, 29, STAR_SIZE_SMALL),
            star(53, 29, STAR_SIZE_SMALL),
            star(43, 37, STAR_SIZE_BIG),
            star(39, 31, STAR_SIZE_SMALL),
            star(19, 28, STAR_SIZE_MEDIUM),
            star(8, 40, STAR_SIZE_MEDIUM),
            star(5, 43, STAR_SIZE_BIG),
            star(18, 62, STAR_SIZE_MEDIUM),
            star(26, 74, STAR_SIZE_BIG),
            star(40, 76, STAR_SIZE_MEDIUM),
            star(37, 67, STAR_SIZE_SMALL),
            star(64, 25, STAR_SIZE_BIG),
            star(70, 10, STAR_SIZE_SMALL),
            star(70, 35, STAR_SIZE_MEDIUM),
            star(68, 48, STAR_SIZE_MEDIUM),
            star(73, 54, STAR_SIZE_SMALL),
            star(80, 37, STAR_SIZE_BIG),
            star(94, 28, STAR_SIZE_SMALL)
        )
        edges = edges(
            0 to 1,
            1 to 2,
            2 to 3,
            2 to 4,
            4 to 5,
            4 to 7,
            5 to 6,
            5 to 15,
            6 to 7,
            7 to 8,
            8 to 9,
            9 to 10,
            10 to 11,
            11 to 12,
            12 to 13,
            13 to 14,
            15 to 16,
            15 to 17,
            17 to 18,
            17 to 20,
            18 to 19,
            20 to 21
        )
    },
    zodiac {
        sign = ZodiacSign.Capricorn
        stars = stars(
            star(49, 8, STAR_SIZE_SMALL),
            star(51, 16, STAR_SIZE_MEDIUM),
            star(70, 62, STAR_SIZE_MEDIUM),
            star(71, 69, STAR_SIZE_BIG) ,
            star(59, 73, STAR_SIZE_MEDIUM),
            star(42, 82, STAR_SIZE_MEDIUM),
            star(24, 87, STAR_SIZE_MEDIUM),
            star(12, 90, STAR_SIZE_BIG),
            star(14, 84, STAR_SIZE_SMALL),
            star(24, 72, STAR_SIZE_MEDIUM),
            star(33, 61, STAR_SIZE_MEDIUM)
        )
        edges = edges(
            0 to 1,
            1 to 2,
            1 to 10,
            2 to 3,
            3 to 4,
            4 to 5,
            5 to 6,
            6 to 7,
            7 to 8,
            8 to 9,
            9 to 10
        )
    },
    zodiac {
        sign = ZodiacSign.Aquarius
        stars = stars(
            star(88, 6, STAR_SIZE_SMALL),
            star(59, 26, STAR_SIZE_BIG),
            star(32, 43, STAR_SIZE_BIG),
            star(56, 52, STAR_SIZE_BIG),
            star(78, 51, STAR_SIZE_SMALL),
            star(31, 54, STAR_SIZE_SMALL),
            star(28, 58, STAR_SIZE_MEDIUM),
            star(23, 63, STAR_SIZE_BIG),
            star(44, 91, STAR_SIZE_BIG),
            star(49, 78, STAR_SIZE_MEDIUM),
            star(65, 76, STAR_SIZE_BIG),
            star(70, 78, STAR_SIZE_MEDIUM),
            star(83, 88, STAR_SIZE_MEDIUM)
        )
        edges = edges(
            0 to 1,
            1 to 2,
            2 to 3,
            2 to 5,
            3 to 4,
            5 to 6,
            6 to 7,
            7 to 8,
            8 to 9,
            9 to 10,
            10 to 11,
            11 to 12
        )
    },
    zodiac {
        sign = ZodiacSign.Ophiuchus
        stars = stars(
            star(2, 25, STAR_SIZE_SMALL),
            star(21, 39, STAR_SIZE_MEDIUM),
            star(32, 52, STAR_SIZE_MEDIUM),
            star(37, 27, STAR_SIZE_SMALL),
            star(40, 24, STAR_SIZE_MEDIUM),
            star(44, 8, STAR_SIZE_BIG),
            star(54, 3, STAR_SIZE_MEDIUM),
            star(63, 14, STAR_SIZE_BIG),
            star(76, 30, STAR_SIZE_BIG),
            star(85, 41, STAR_SIZE_MEDIUM),
            star(97, 24, STAR_SIZE_SMALL),
            star(83, 43, STAR_SIZE_MEDIUM),
            star(73, 55, STAR_SIZE_MEDIUM),
            star(56, 64, STAR_SIZE_MEDIUM),
            star(43, 64, STAR_SIZE_SMALL),
            star(51, 83, STAR_SIZE_BIG)
        )
        edges = edges(
            0 to 1,
            1 to 2,
            2 to 3,
            2 to 14,
            3 to 4,
            4 to 5,
            5 to 6,
            6 to 7,
            7 to 8,
            8 to 9,
            9 to 10,
            9 to 11,
            11 to 12,
            12 to 13,
            13 to 15,
            13 to 14
        )
    }
)

internal object ZodiacGraphs {

    operator fun get(key: ZodiacSign): ZodiacGraph =
        graphs[key] ?: throw IllegalArgumentException("Sign $key is not supported")
}