package com.example.ula_app.data

import com.example.ula_app.data.dataclass.MonsterMovie
import kotlinx.datetime.LocalTime


object DataSource {

    // Home tab data sources
    enum class MonsterAgeOptions {
        Egg,
        Child,
        Adult
    }

    enum class MonsterBodyStatusOptions {
        NA,
        Normal,
        Overweight,
        Fat,
        Fit
    }

    val childThreshold: List<Double> = listOf(
        0.8,
        1.0
    )

    val adultThreshold: List<Double> = listOf(
        0.6,
        0.8,
        1.2
    )

    val progressBarThreshold: List<Double> = listOf(
        0.4,
        0.6,
        0.8,
        1.0,
        1.2
    )

    val daysToAges: List<Int> = listOf(
        1,
        5
    )

    val timeThreshold: List<LocalTime> = listOf(
        LocalTime(hour = 11, minute = 59, second = 59),
        LocalTime(hour = 23, minute = 59, second = 59)
    )

    val eggRule = listOf(
        "0_0",
        "1_1",
        "1_2",
        "2_1",
        "2_2",
        "2_3",
        "2_4",
        "3_1",
        "3_2",
        "3_3",
        "4_1",
//        "5_1",
//        "5_2",
        "5_3",
    )

    val childNormalRule = listOf(
        "6_1",
        "6_2",
        "6_3",
        "7_1_1",
        "7_1_2",
        "7_2",
        "7_3",
        "8_1",
        "8_2",
        "8_3",

        )

    val childFatRule = listOf(
        "11_1_1",
        "11_1_2",
        "11_1_3",
        "11_2_1",
        "11_2_2",
        "11_2_3",
        "12_1_1",
        "12_1_2",
        "12_1_3",
        "12_2_1",
        "12_3_1",
        "12_3_2"
    )

    val childOverweightRule = listOf(
        "9_1",
        "9_2",
        "9_3",
        "10_1",
        "10_2",
        "10_3"
    )

    val adultNormalRule= listOf(
        "13_1",
        "13_2",
        "13_3",
        "14_1_1",
        "14_1_2",
        "14_2_1",
        "14_2_2",
        "14_3_1",
        "14_3_2",
        "15_1_1",
        "15_1_2",
        "15_2",
        "15_3",
        "16_1_1",
        "16_1_2",
        "16_2_2",
        "16_3"
    )

    val adultFatRule = listOf(
        "21_1",
        "21_2",
        "21_3",
        "22_1_1",
        "22_1_2",
        "22_2",
        "23_1",
        "23_2",
        "23_3"
    )

    val adultFitRule = listOf(
        "24_1",
        "24_2",
        "24_3",
        "24_4",
        "24_5",
        "25_1",
        "25_2",
        "25_3",
        "26_1",
        "26_2_1",
        "26_2_2",
        "26_3",
        "26_4"
    )

    val adultOverweightRule = listOf(
        "17_1_1",
        "17_1_2",
        "17_2",
        "17_3",
        "18_1_1",
        "18_1_2",
        "18_2",
        "18_3",
        "19_1",
        "19_2",
        "19_3",
        "20_1_1",
        "20_1_2",
        "20_2",
        "20_3"
    )

}