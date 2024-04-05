package ru.directum.maestro.Presentation.Model

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import ru.directum.maestro.util.CoroutineViewModel

class WebViewViewModel: CoroutineViewModel(), KoinComponent {
    private val _state = MutableStateFlow(
        WebViewUIState(
            loading = false,
            loadingProgressValue = 0f
        )
    )
    val state: StateFlow<WebViewUIState?> = _state

    fun changeLoadingProgressValue(value: Int) {
        _state.value = _state.value.copy(loadingProgressValue = value.toFloat())
    }

    fun changeLoadingState(loading: Boolean) {
        _state.value = _state.value.copy(loading = loading)
    }
}