package com.example.ula_app.data.dataclass

import com.example.ula_app.data.DataSource

data class MonsterCurrentStatus (
    val id: String = "11_1_1",
    val age: String = DataSource.MonsterAgeOptions.Egg.name,
    val bodyStatus: String = DataSource.MonsterBodyStatusOptions.NA.name,
    val tapCounter: Int = 0,
    val openDialog: Boolean = false
)