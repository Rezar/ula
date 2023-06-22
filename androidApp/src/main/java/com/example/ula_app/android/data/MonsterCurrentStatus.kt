package com.example.ula_app.android.data

data class MonsterCurrentStatus (
    val id: String = "6_1",
    val age: String = DataSource.MonsterAgeOptions.Egg.name,
    val bodyStatus: String = DataSource.MonsterBodyStatusOptions.NA.name,
    val tapCounter: Int = 0
)