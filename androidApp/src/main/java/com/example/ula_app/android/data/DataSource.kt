package com.example.ula_app.android.data

import com.example.ula_app.R
import kotlinx.datetime.LocalTime


object DataSource {

    val timeThreshold: List<LocalTime> = listOf(
        LocalTime(hour = 11, minute = 59, second = 59),
        LocalTime(hour = 23, minute = 59, second = 59)
    )

    val bottomNavigationBarItem: List<BottomNavigationBarItem> = listOf(
        BottomNavigationBarItem(
            icon = R.drawable.ic_ula,
            name = "Home",
            route = "home",
            scale = 1f
        ),
        BottomNavigationBarItem(
            icon = R.drawable.ic_stats,
            name = "Stats",
            route = "stats",
            scale = 1f
        ),
        BottomNavigationBarItem(
            icon = R.drawable.ic_help,
            name = "Help",
            route = "help",
            scale = 1f
        ),
        BottomNavigationBarItem(
            icon = R.drawable.ic_cog,
            name = "Setting",
            route = "setting",
            scale = 1f
        ),
        BottomNavigationBarItem(
            icon = R.drawable.ic_bug,
            name = "Debug",
            route = "debug",
            scale = 1f
        )
    )

    val androidResourcePath = "android.resource://com.example.ulp_app/"
}