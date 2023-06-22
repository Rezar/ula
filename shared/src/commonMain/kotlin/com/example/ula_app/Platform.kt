package com.example.ula_app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform