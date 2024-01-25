package com.example.ula_app.presentation

import com.example.ula_app.data.dataclass.CommonMonsterMovie

actual class UiMonsterMovie(
    val uri: String,
    actual val commonMonsterMovie: CommonMonsterMovie
) {

    actual companion object {

        actual val allMonsterMovie: Map<String, UiMonsterMovie>
            get() = CommonMonsterMovie.values().map{mm ->

                UiMonsterMovie(
                    uri = "v" + mm.monsterMovie.id,
                    commonMonsterMovie = mm

                )
            }.associateBy {it.commonMonsterMovie.monsterMovie.id}

    }

}