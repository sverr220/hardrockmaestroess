package ru.directum.maestro.settings

interface SettingsFactory {
    suspend fun createCommonSettings(): Settings
    suspend fun createEncryptedSettings(): Settings
}