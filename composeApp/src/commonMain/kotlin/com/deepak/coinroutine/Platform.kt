package com.deepak.coinroutine

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform