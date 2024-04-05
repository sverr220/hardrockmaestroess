package ru.directum.maestro.Presentation.Model

data class WebViewUIState internal constructor(
    val loading: Boolean = false,
    val loadingProgressValue: Float = 0f
)