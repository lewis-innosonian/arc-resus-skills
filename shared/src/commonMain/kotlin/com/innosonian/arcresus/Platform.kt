package com.innosonian.arcresus

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform