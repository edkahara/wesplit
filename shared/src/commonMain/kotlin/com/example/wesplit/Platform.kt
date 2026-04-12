package com.example.wesplit

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform