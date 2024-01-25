package com.example.ula_app.presentation

import com.example.ula_app.data.dataclass.CommonMonsterMovie

expect class UiMonsterMovie {

    val commonMonsterMovie: CommonMonsterMovie

    companion object{
        val allMonsterMovie: Map<String, UiMonsterMovie>
    }

}