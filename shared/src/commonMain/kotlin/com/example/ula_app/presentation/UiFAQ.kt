package com.example.ula_app.presentation

import com.example.ula_app.data.dataclass.CommonFAQ

expect class UiFAQ {

    val faq: CommonFAQ

    companion object {
        val allFAQs: List<UiFAQ>
    }
}