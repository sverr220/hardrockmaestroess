package ru.directum.maestro.Presentation.Model

data class  InputURLUiState internal constructor(
    val url: String = "",
    val loading: Boolean = false,
    val error: Boolean = false,
    val errorMessage: String = "",
    val success: Boolean = false,
)