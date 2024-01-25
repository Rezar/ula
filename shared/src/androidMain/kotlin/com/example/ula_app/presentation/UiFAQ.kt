package com.example.ula_app.presentation

import com.example.ula_app.data.dataclass.CommonFAQ


actual data class UiFAQ(
    actual val faq: CommonFAQ
) {

    actual companion object {
        actual val allFAQs: List<UiFAQ>
            get() = CommonFAQ.values().map { faq ->
                UiFAQ(
                    faq = faq
                )

            }
    }
}