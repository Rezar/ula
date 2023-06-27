package com.example.ula_app.android.data

import android.net.Uri
import com.example.ula_app.android.R
import kotlinx.datetime.LocalTime


object DataSource {

    // Welcome data sources
    val stepOptions = listOf(
        5000,
        10000,
        15000,
        20000
    )

    // Help tab data sources
    data class FAQ(val question: Int, val answer: Int)
    val questionsAnswers: List<FAQ> = listOf(
        FAQ(
            question = R.string.FAQs1,
            answer = R.string.A1
        ),
        FAQ(
            question = R.string.FAQs2,
            answer = R.string.A2
        ),
        FAQ(
            question = R.string.FAQs3,
            answer = R.string.A3
        ),
        FAQ(
            question = R.string.FAQs4,
            answer = R.string.A4
        ),
        FAQ(
            question = R.string.FAQs5,
            answer = R.string.A5
        ),
        FAQ(
            question = R.string.FAQs6,
            answer = R.string.A6
        ),
    )


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

    val daysToAges: List<Int> = listOf(
        1,
        5
    )

    val timeThreshold: List<LocalTime> = listOf(
        LocalTime(hour = 11, minute = 59, second = 59),
        LocalTime(hour = 23, minute = 59, second = 59)
    )

    val bottomNavigationBarItem: List<BottomNavigationBarItem> = listOf(
        BottomNavigationBarItem(
            icon = R.drawable.ic_ula,
            name = "Home",
            route = "home"
        ),
        BottomNavigationBarItem(
            icon = R.drawable.ic_stats,
            name = "Stats",
            route = "stats"
        ),
        BottomNavigationBarItem(
            icon = R.drawable.ic_help,
            name = "Help",
            route = "help"
        ),
        BottomNavigationBarItem(
            icon = R.drawable.ic_cog,
            name = "Setting",
            route = "setting"
        ),
        BottomNavigationBarItem(
            icon = R.drawable.ic_bug,
            name = "Debug",
            route = "debug"
        )
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

    val androidResourcePath = "android.resource://com.example.ulp_app/"

    val monsterMovies: Map<String, MonsterMovie> = mapOf(
        "0_0" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v0_0),
            "0_0",
            1,
            1,
            false,
            true,
            MonsterAgeOptions.Egg.name,
            MonsterBodyStatusOptions.NA.name
        ),
        "1_1" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v1_1),
            "1_1",
            1,
            1,
            false,
            false,
            MonsterAgeOptions.Egg.name,
            MonsterBodyStatusOptions.NA.name
        ),
        "1_2" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v1_2),
            "1_2",
            1,
            1,
            false,
            false,
            MonsterAgeOptions.Egg.name,
            MonsterBodyStatusOptions.NA.name
        ),
        "2_1" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v2_1),
            "2_1",
            1,
            2,
            false,
            false,
            MonsterAgeOptions.Egg.name,
            MonsterBodyStatusOptions.NA.name
        ),
        "2_2" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v2_2),
            "2_2",
            1,
            2,
            false,
            false,
            MonsterAgeOptions.Egg.name,
            MonsterBodyStatusOptions.NA.name
        ),
        "2_3" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v2_3),
            "2_3",
            1,
            2,
            false,
            false,
            MonsterAgeOptions.Egg.name,
            MonsterBodyStatusOptions.NA.name
        ),
        "2_4" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v2_4),
            "2_4",
            1,
            2,
            false,
            false,
            MonsterAgeOptions.Egg.name,
            MonsterBodyStatusOptions.NA.name
        ),
        "3_1" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v3_1),
            "3_1",
            1,
            3,
            false,
            false,
            MonsterAgeOptions.Egg.name,
            MonsterBodyStatusOptions.NA.name
        ),
        "3_2" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v3_2),
            "3_2",
            1,
            3,
            false,
            false,
            MonsterAgeOptions.Egg.name,
            MonsterBodyStatusOptions.NA.name
        ),
        "3_3" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v3_3),
            "3_3",
            1,
            3,
            false,
            false,
            MonsterAgeOptions.Egg.name,
            MonsterBodyStatusOptions.NA.name
        ),
        "4_1" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v4_1),
            "4_1",
            1,
            4,
            false,
            false,
            MonsterAgeOptions.Egg.name,
            MonsterBodyStatusOptions.NA.name
        ),
/*        "5_1" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v5_1),
            "5_1",
            0,
            5,
            false,
            true,
            MonsterAgeOptions.Egg.name,
            MonsterBodyStatusOptions.NA.name
        ),
        "5_2" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v5_2),
            "5_2",
            1,
            5,
            false,
            true,
            MonsterAgeOptions.Egg.name,
            MonsterBodyStatusOptions.NA.name
        ),*/
        "5_3" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v5_3),
            "5_3",
            1,
            5,
            true,
            true,
            MonsterAgeOptions.Egg.name,
            MonsterBodyStatusOptions.NA.name
        ),
        "6_1" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v6_1),
            "6_1",
            1,
            6,
            false,
            true,
            MonsterAgeOptions.Child.name,
            MonsterBodyStatusOptions.Normal.name
        ),
        "6_2" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v6_2),
            "6_2",
            3,
            6,
            false,
            false,
            MonsterAgeOptions.Child.name,
            MonsterBodyStatusOptions.Normal.name
        ),
        "6_3" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v6_3),
            "6_3",
            1,
            6,
            true,
            false,
            MonsterAgeOptions.Child.name,
            MonsterBodyStatusOptions.Normal.name
        ),
        "7_1_1" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v7_1_1),
            "7_1_1",
            1,
            7,
            false,
            true,
            MonsterAgeOptions.Child.name,
            MonsterBodyStatusOptions.Normal.name
        ),
        "7_1_2" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v7_1_2),
            "7_1_2",
            2,
            7,
            false,
            false,
            MonsterAgeOptions.Child.name,
            MonsterBodyStatusOptions.Normal.name
        ),
        "7_2" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v7_2),
            "7_2",
            3,
            7,
            false,
            false,
            MonsterAgeOptions.Child.name,
            MonsterBodyStatusOptions.Normal.name
        ),
        "7_3" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v7_3),
            "7_3",
            1,
            7,
            true,
            false,
            MonsterAgeOptions.Child.name,
            MonsterBodyStatusOptions.Normal.name
        ),
        "8_1" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v8_1),
            "8_1",
            1,
            8,
            false,
            true,
            MonsterAgeOptions.Child.name,
            MonsterBodyStatusOptions.Normal.name
        ),
        "8_2" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v8_2),
            "8_2",
            1,
            8,
            false,
            false,
            MonsterAgeOptions.Child.name,
            MonsterBodyStatusOptions.Normal.name
        ),
        "8_3" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v8_3),
            "8_3",
            1,
            8,
            true,
            false,
            MonsterAgeOptions.Child.name,
            MonsterBodyStatusOptions.Normal.name
        ),
        "9_1" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v9_1),
            "9_1",
            1,
            9,
            false,
            true,
            MonsterAgeOptions.Child.name,
            MonsterBodyStatusOptions.Overweight.name
        ),
        "9_2" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v9_2),
            "9_2",
            3,
            9,
            false,
            false,
            MonsterAgeOptions.Child.name,
            MonsterBodyStatusOptions.Overweight.name
        ),
        "9_3" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v9_3),
            "9_3",
            1,
            9,
            true,
            false,
            MonsterAgeOptions.Child.name,
            MonsterBodyStatusOptions.Overweight.name
        ),
        "10_1" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v10_1),
            "10_1",
            1,
            10,
            false,
            true,
            MonsterAgeOptions.Child.name,
            MonsterBodyStatusOptions.Overweight.name
        ),
        "10_2" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v10_2),
            "10_2",
            2,
            10,
            false,
            false,
            MonsterAgeOptions.Child.name,
            MonsterBodyStatusOptions.Overweight.name
        ),
        "10_3" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v10_3),
            "10_3",
            1,
            10,
            true,
            false,
            MonsterAgeOptions.Child.name,
            MonsterBodyStatusOptions.Overweight.name
        ),
        "11_1_1" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v11_1_1),
            "11_1_1",
            1,
            11,
            false,
            true,
            MonsterAgeOptions.Child.name,
            MonsterBodyStatusOptions.Fat.name
        ),
        "11_1_2" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v11_1_2),
            "11_1_2",
            2,
            11,
            false,
            false,
            MonsterAgeOptions.Child.name,
            MonsterBodyStatusOptions.Fat.name
        ),
        "11_1_3" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v11_1_3),
            "11_1_3",
            2,
            11,
            true,
            false,
            MonsterAgeOptions.Child.name,
            MonsterBodyStatusOptions.Fat.name
        ),
        "11_2_1" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v11_2_1),
            "11_2_1",
            1,
            11,
            false,
            true,
            MonsterAgeOptions.Child.name,
            MonsterBodyStatusOptions.Fat.name
        ),
        "11_2_2" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v11_2_2),
            "11_2_2",
            2,
            11,
            false,
            false,
            MonsterAgeOptions.Child.name,
            MonsterBodyStatusOptions.Fat.name
        ),
        "11_2_3" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v11_2_3),
            "11_2_3",
            1,
            11,
            true,
            false,
            MonsterAgeOptions.Child.name,
            MonsterBodyStatusOptions.Fat.name
        ),
        "12_1_1" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v12_1_1),
            "12_1_1",
            0,
            12,
            false,
            false,
            MonsterAgeOptions.Child.name,
            MonsterBodyStatusOptions.Fat.name
        ),
        "12_1_2" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v12_1_2),
            "12_1_2",
            1,
            12,
            false,
            true,
            MonsterAgeOptions.Child.name,
            MonsterBodyStatusOptions.Fat.name
        ),
        "12_1_3" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v12_1_3),
            "12_1_3",
            3,
            12,
            false,
            false,
            MonsterAgeOptions.Child.name,
            MonsterBodyStatusOptions.Fat.name
        ),
        "12_2_1" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v12_2_1),
            "12_2_1",
            3,
            12,
            false,
            false,
            MonsterAgeOptions.Child.name,
            MonsterBodyStatusOptions.Fat.name
        ),
        "12_3_1" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v12_3_1),
            "12_3_1",
            0,
            12,
            false,
            false,
            MonsterAgeOptions.Child.name,
            MonsterBodyStatusOptions.Fat.name
        ),
        "12_3_2" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v12_3_2),
            "12_3_2",
            1,
            12,
            true,
            false,
            MonsterAgeOptions.Child.name,
            MonsterBodyStatusOptions.Fat.name
        ),
        "13_1" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v13_1),
            "13_1",
            1,
            13,
            false,
            false,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Normal.name
        ),
        "13_2" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v13_2),
            "13_2",
            3,
            13,
            false,
            false,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Normal.name
        ),
        "13_3" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v13_3),
            "13_3",
            1,
            13,
            true,
            false,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Normal.name
        ),
        "14_1_1" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v14_1_1),
            "14_1_1",
            1,
            14,
            false,
            true,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Normal.name
        ),
        "14_1_2" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v14_1_2),
            "14_1_2",
            3,
            14,
            false,
            false,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Normal.name
        ),
        "14_2_1" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v14_2_1),
            "14_2_1",
            1,
            14,
            false,
            true,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Normal.name
        ),
        "14_2_2" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v14_2_2),
            "14_2_2",
            2,
            14,
            false,
            false,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Normal.name
        ),
        "14_3_1" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v14_3_1),
            "14_3_1",
            1,
            14,
            false,
            true,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Normal.name
        ),
        "14_3_2" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v14_3_2),
            "14_3_2",
            1,
            14,
            true,
            false,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Normal.name
        ),
        "15_1_1" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v15_1_1),
            "15_1_1",
            1,
            15,
            false,
            true,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Normal.name
        ),
        "15_1_2" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v15_1_2),
            "15_1_2",
            3,
            15,
            false,
            false,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Normal.name
        ),
        "15_2" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v15_2),
            "15_2",
            2,
            15,
            false,
            false,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Normal.name
        ),
        "15_3" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v15_3),
            "15_3",
            1,
            15,
            true,
            false,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Normal.name
        ),
        "16_1_1" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v16_1_1),
            "16_1_1",
            1,
            16,
            false,
            true,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Normal.name
        ),
        "16_1_2" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v16_1_2),
            "16_1_2",
            3,
            16,
            false,
            false,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Normal.name
        ),
        "16_2_2" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v16_2_2),
            "16_2_2",
            3,
            16,
            false,
            false,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Normal.name
        ),
        "16_3" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v16_3),
            "16_3",
            1,
            16,
            true,
            false,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Normal.name
        ),
        "17_1_1" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v17_1_1),
            "17_1_1",
            1,
            17,
            false,
            true,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Overweight.name
        ),
        "17_1_2" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v17_1_2),
            "17_1_2",
            3,
            17,
            false,
            false,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Overweight.name
        ),
        "17_2" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v17_2),
            "17_2",
            1,
            17,
            false,
            false,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Overweight.name
        ),
        "17_3" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v17_3),
            "17_3",
            1,
            17,
            true,
            false,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Overweight.name
        ),
        "18_1_1" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v18_1_1),
            "18_1_1",
            1,
            18,
            false,
            true,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Overweight.name
        ),
        "18_1_2" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v18_1_2),
            "18_1_2",
            3,
            18,
            false,
            false,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Overweight.name
        ),
        "18_2" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v18_2),
            "18_2",
            3,
            18,
            false,
            false,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Overweight.name
        ),
        "18_3" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v18_3),
            "18_3",
            1,
            18,
            true,
            false,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Overweight.name
        ),
        "19_1" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v19_1),
            "19_1",
            1,
            19,
            false,
            true,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Overweight.name
        ),
        "19_2" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v19_2),
            "19_2",
            3,
            19,
            false,
            false,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Overweight.name
        ),
        "19_3" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v19_3),
            "19_3",
            1,
            19,
            true,
            false,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Overweight.name
        ),
        "20_1_1" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v20_1_1),
            "20_1_1",
            1,
            20,
            false,
            true,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Overweight.name
        ),

        "20_1_2" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v20_1_2),
            "20_1_2",
            3,
            20,
            false,
            false,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Overweight.name
        ),

        "20_2" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v20_2),
            "20_2",
            2,
            20,
            false,
            false,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Overweight.name
        ),

        "20_3" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v20_3),
            "20_3",
            1,
            20,
            true,
            false,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Overweight.name
        ),
        "21_1" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v21_1),
            "21_1",
            1,
            21,
            false,
            true,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Fat.name
        ),
        "21_2" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v21_2),
            "21_2",
            2,
            21,
            false,
            false,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Fat.name
        ),
        "21_3" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v21_3),
            "21_3",
            1,
            21,
            true,
            false,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Fat.name
        ),
        "22_1_1" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v22_1_1),
            "22_1_1",
            1,
            22,
            false,
            true,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Fat.name
        ),
        "22_1_2" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v22_1_2),
            "22_1_2",
            3,
            22,
            false,
            false,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Fat.name
        ),
        "22_2" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v22_2),
            "22_2",
            2,
            22,
            true,
            false,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Fat.name
        ),
        "23_1" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v23_1),
            "23_1",
            1,
            23,
            false,
            true,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Fat.name
        ),
        "23_2" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v23_2),
            "23_2",
            3,
            23,
            false,
            false,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Fat.name
        ),
        "23_3" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v23_3),
            "23_3",
            1,
            23,
            true,
            false,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Fat.name
        ),
        "24_1" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v24_1),
            "24_1",
            1,
            24,
            false,
            false,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Fit.name
        ),
        "24_2" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v24_2),
            "24_2",
            2,
            24,
            false,
            false,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Fit.name
        ),
        "24_3" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v24_3),
            "24_3",
            2,
            24,
            false,
            false,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Fit.name
        ),
        "24_4" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v24_4),
            "24_4",
            2,
            24,
            false,
            false,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Fit.name
        ),
        "24_5" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v24_5),
            "24_5",
            1,
            24,
            false,
            true,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Fit.name
        ),
        "25_1" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v25_1),
            "25_1",
            1,
            25,
            false,
            false,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Fit.name
        ),
        "25_2" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v25_2),
            "25_2",
            3,
            25,
            false,
            false,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Fit.name
        ),
        "25_3" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v25_3),
            "25_3",
            1,
            25,
            true,
            false,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Fit.name
        ),
        "26_1" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v26_1),
            "26_1",
            1,
            26,
            false,
            false,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Fit.name
        ),
        "26_2_1" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v26_2_1),
            "26_2_1",
            1,
            26,
            false,
            false,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Fit.name
        ),
        "26_2_2" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v26_2_2),
            "26_2_2",
            3,
            26,
            false,
            false,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Fit.name
        ),
        "26_3" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v26_3),
            "26_3",
            3,
            26,
            false,
            false,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Fit.name
        ),
        "26_4" to MonsterMovie(
            Uri.parse(androidResourcePath + R.raw.v26_4),
            "26_4",
            3,
            26,
            false,
            true,
            MonsterAgeOptions.Adult.name,
            MonsterBodyStatusOptions.Fit.name
        )
    )
}