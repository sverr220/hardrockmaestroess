package ru.directum.maestro

class NativeData {
    private val platform: Platform = getPlatform()

    fun platformName(): String {
        return platform.name
    }
}