package ru.directum.maestro

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform