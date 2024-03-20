package ru.directum.maestro.util

import kotlinx.coroutines.CoroutineScope

expect abstract class CoroutineViewModel() {
    val coroutineScope: CoroutineScope
    fun dispose()
    protected fun onCleared()
}