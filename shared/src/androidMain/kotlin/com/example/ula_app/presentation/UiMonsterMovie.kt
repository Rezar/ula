package com.example.ula_app.presentation

import android.net.Uri
import com.example.ula_app.R
import com.example.ula_app.data.dataclass.CommonMonsterMovie

actual data class UiMonsterMovie(
    val uri: Int,
    actual val commonMonsterMovie: CommonMonsterMovie
) {

    actual companion object{
        actual val allMonsterMovie: Map<String, UiMonsterMovie>
            get() = CommonMonsterMovie.values().map {mm ->

                UiMonsterMovie(
                    uri = when(mm) {
                        CommonMonsterMovie.MOVIE1 -> R.raw.v0_0
                        CommonMonsterMovie.MOVIE2 -> R.raw.v1_1
                        CommonMonsterMovie.MOVIE3 -> R.raw.v1_2
                        CommonMonsterMovie.MOVIE4 -> R.raw.v2_1
                        CommonMonsterMovie.MOVIE5 -> R.raw.v2_2
                        CommonMonsterMovie.MOVIE6 -> R.raw.v2_3
                        CommonMonsterMovie.MOVIE7 -> R.raw.v2_4
                        CommonMonsterMovie.MOVIE8 -> R.raw.v3_1
                        CommonMonsterMovie.MOVIE9 -> R.raw.v3_2
                        CommonMonsterMovie.MOVIE10 -> R.raw.v3_3
                        CommonMonsterMovie.MOVIE11 -> R.raw.v4_1
                        CommonMonsterMovie.MOVIE12 -> R.raw.v5_3
                        CommonMonsterMovie.MOVIE13 -> R.raw.v6_1
                        CommonMonsterMovie.MOVIE14 -> R.raw.v6_2
                        CommonMonsterMovie.MOVIE15 -> R.raw.v6_3
                        CommonMonsterMovie.MOVIE16 -> R.raw.v7_1_1
                        CommonMonsterMovie.MOVIE17 -> R.raw.v7_1_2
                        CommonMonsterMovie.MOVIE18 -> R.raw.v7_2
                        CommonMonsterMovie.MOVIE19 -> R.raw.v7_3
                        CommonMonsterMovie.MOVIE20 -> R.raw.v8_1
                        CommonMonsterMovie.MOVIE21 -> R.raw.v8_2
                        CommonMonsterMovie.MOVIE22 -> R.raw.v8_3
                        CommonMonsterMovie.MOVIE23 -> R.raw.v9_1
                        CommonMonsterMovie.MOVIE24 -> R.raw.v9_2
                        CommonMonsterMovie.MOVIE25 -> R.raw.v9_3
                        CommonMonsterMovie.MOVIE26 -> R.raw.v10_1
                        CommonMonsterMovie.MOVIE27 -> R.raw.v10_2
                        CommonMonsterMovie.MOVIE28 -> R.raw.v10_3
                        CommonMonsterMovie.MOVIE29 -> R.raw.v11_1_1
                        CommonMonsterMovie.MOVIE30 -> R.raw.v11_1_2
                        CommonMonsterMovie.MOVIE31 -> R.raw.v11_1_3
                        CommonMonsterMovie.MOVIE32 -> R.raw.v11_2_1
                        CommonMonsterMovie.MOVIE33 -> R.raw.v11_2_2
                        CommonMonsterMovie.MOVIE34 -> R.raw.v11_2_3
                        CommonMonsterMovie.MOVIE35 -> R.raw.v12_1_1
                        CommonMonsterMovie.MOVIE36 -> R.raw.v12_1_2
                        CommonMonsterMovie.MOVIE37 -> R.raw.v12_1_3
                        CommonMonsterMovie.MOVIE38 -> R.raw.v12_2_1
                        CommonMonsterMovie.MOVIE39 -> R.raw.v12_3_1
                        CommonMonsterMovie.MOVIE40 -> R.raw.v12_3_2
                        CommonMonsterMovie.MOVIE41 -> R.raw.v13_1
                        CommonMonsterMovie.MOVIE42 -> R.raw.v13_2
                        CommonMonsterMovie.MOVIE43 -> R.raw.v13_3
                        CommonMonsterMovie.MOVIE44 -> R.raw.v14_1_1
                        CommonMonsterMovie.MOVIE45 -> R.raw.v14_1_2
                        CommonMonsterMovie.MOVIE46 -> R.raw.v14_2_1
                        CommonMonsterMovie.MOVIE47 -> R.raw.v14_2_2
                        CommonMonsterMovie.MOVIE48 -> R.raw.v14_3_1
                        CommonMonsterMovie.MOVIE49 -> R.raw.v14_3_2
                        CommonMonsterMovie.MOVIE50 -> R.raw.v15_1_1
                        CommonMonsterMovie.MOVIE51 -> R.raw.v15_1_2
                        CommonMonsterMovie.MOVIE52 -> R.raw.v15_2
                        CommonMonsterMovie.MOVIE53 -> R.raw.v15_3
                        CommonMonsterMovie.MOVIE54 -> R.raw.v16_1_1

                        CommonMonsterMovie.MOVIE56 -> R.raw.v16_1_2
                        CommonMonsterMovie.MOVIE57 -> R.raw.v16_2_2
                        CommonMonsterMovie.MOVIE58 -> R.raw.v16_3
                        CommonMonsterMovie.MOVIE59 -> R.raw.v17_1_1
                        CommonMonsterMovie.MOVIE60 -> R.raw.v17_1_2
                        CommonMonsterMovie.MOVIE61 -> R.raw.v17_2
                        CommonMonsterMovie.MOVIE62 -> R.raw.v17_3
                        CommonMonsterMovie.MOVIE63 -> R.raw.v18_1_1
                        CommonMonsterMovie.MOVIE64 -> R.raw.v18_1_2
                        CommonMonsterMovie.MOVIE65 -> R.raw.v18_2
                        CommonMonsterMovie.MOVIE66 -> R.raw.v18_3
                        CommonMonsterMovie.MOVIE67 -> R.raw.v19_1
                        CommonMonsterMovie.MOVIE68 -> R.raw.v19_2
                        CommonMonsterMovie.MOVIE69 -> R.raw.v19_3
                        CommonMonsterMovie.MOVIE70 -> R.raw.v20_1_1
                        CommonMonsterMovie.MOVIE71 -> R.raw.v20_1_2
                        CommonMonsterMovie.MOVIE72 -> R.raw.v20_2
                        CommonMonsterMovie.MOVIE73 -> R.raw.v20_3
                        CommonMonsterMovie.MOVIE74 -> R.raw.v21_1
                        CommonMonsterMovie.MOVIE75 -> R.raw.v21_2
                        CommonMonsterMovie.MOVIE76 -> R.raw.v21_3
                        CommonMonsterMovie.MOVIE77 -> R.raw.v22_1_1
                        CommonMonsterMovie.MOVIE78 -> R.raw.v22_1_2
                        CommonMonsterMovie.MOVIE79 -> R.raw.v22_2
                        CommonMonsterMovie.MOVIE80 -> R.raw.v23_1
                        CommonMonsterMovie.MOVIE81 -> R.raw.v23_2
                        CommonMonsterMovie.MOVIE82 -> R.raw.v23_3
                        CommonMonsterMovie.MOVIE83 -> R.raw.v24_1
                        CommonMonsterMovie.MOVIE84 -> R.raw.v24_2
                        CommonMonsterMovie.MOVIE85 -> R.raw.v24_3
                        CommonMonsterMovie.MOVIE86 -> R.raw.v24_4
                        CommonMonsterMovie.MOVIE87 -> R.raw.v24_5
                        CommonMonsterMovie.MOVIE88 -> R.raw.v25_1
                        CommonMonsterMovie.MOVIE89 -> R.raw.v25_2
                        CommonMonsterMovie.MOVIE90 -> R.raw.v25_3
                        CommonMonsterMovie.MOVIE91 -> R.raw.v26_1
                        CommonMonsterMovie.MOVIE92 -> R.raw.v26_2_1
                        CommonMonsterMovie.MOVIE93 -> R.raw.v26_2_2
                        CommonMonsterMovie.MOVIE94 -> R.raw.v26_3
                        CommonMonsterMovie.MOVIE95 -> R.raw.v26_4


                    },
                    commonMonsterMovie = mm
                )
            }.associateBy {it.commonMonsterMovie.monsterMovie.id}
    }

}