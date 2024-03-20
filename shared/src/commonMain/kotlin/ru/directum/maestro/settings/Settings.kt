package ru.directum.maestro.settings

interface Settings {
    suspend fun exist(key: String): Boolean
    suspend fun get(key: String): Any?
    suspend fun put(key: String, value: Any)
    suspend fun remove(key: String)
}