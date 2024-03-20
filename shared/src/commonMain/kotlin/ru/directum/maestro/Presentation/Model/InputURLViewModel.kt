package ru.directum.maestro.Presentation.Model

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import ru.directum.maestro.util.CoroutineViewModel

class InputURLViewModel: CoroutineViewModel(), KoinComponent {
    private val _state = MutableStateFlow(
        InputURLUiState(
            url = "https://lk.directum.ru/",
            loading = false,
            error = false,
            success = false
        )
    )
    val state: StateFlow<InputURLUiState?> = _state

    fun connect(url: String) {
        coroutineScope.launch {
            _state.value = _state.value.copy(
                loading = true,
                error = false)

            // Проверить URL
            delay(1000)
            // Провести подключение

            _state.value = _state.value.copy(
                success = true,
                url = url,
                loading = false,
                error = false)
        }
    }

    @Suppress("unused")
    fun observeUpload(onChange: (InputURLUiState?) -> Unit) {
        state.onEach {
            onChange(it)
        }.launchIn(coroutineScope)
    }

    fun resetError() {
        _state.value.copy(
            success = false,
            loading = false,
            error = false)
    }
}