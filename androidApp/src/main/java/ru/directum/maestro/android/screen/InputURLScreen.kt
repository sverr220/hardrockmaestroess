package ru.directum.maestro.android.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.directum.maestro.Presentation.Model.InputURLViewModel
import ru.directum.maestro.R

@Composable
fun InputURLScreen(showWebView: (url: String) -> Unit,
                   showProfileScreen: () -> Unit,
                   showPinCodeScreen: () -> Unit) {
    val viewModel = remember { InputURLViewModel() }
    val state by viewModel.state.collectAsState()

    when {
        state == null -> {}
        state!!.success -> {
            showWebView(state!!.url)
        }

        state!!.loading -> ProgressIndicator()
        state!!.error -> Error(message = state!!.errorMessage, close = viewModel::resetError)
        else -> InputEditBox(state!!.url, viewModel::connect, showProfileScreen, showPinCodeScreen )
    }
}

@Preview
@Composable
private fun InputEditBox(url: String = "", connectToUrl: (url: String) -> Unit = {},
                         showProfileScreen: () -> Unit = {},
                         showPinCodeScreen: () -> Unit = {}) {
    var text by rememberSaveable { mutableStateOf(url) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .imePadding()
            .wrapContentSize(Alignment.Center)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center),
        ) {
            Image(
                painter = painterResource(id = ru.directum.maestro.android.R.drawable.splash),
                contentDescription = "splash"
            )
        }
        TextField(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            value = text,
            onValueChange = {
                text = it
            },
            label = { Text("Введите адрес сервера:") }
        )
        Row(
            modifier = Modifier
                .padding(0.dp, 0.dp, 10.dp, 0.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    showProfileScreen()
                },
                border = BorderStroke(1.dp, Color.Black),
            ) {
                Text("Профиль")
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                onClick = {
                    showPinCodeScreen()
                },
                border = BorderStroke(1.dp, Color.Black),
            ) {
                Text("Pin")
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                onClick = {
                    connectToUrl(text)
                },
                border = BorderStroke(1.dp, Color.Black),
            ) {
                Text("Подключиться")
            }
        }
    }
}

@Composable
private fun ProgressIndicator() {
    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.Center),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.then(Modifier.size(128.dp)),
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
        Text(
            textAlign = TextAlign.Center,
            text = "Подключение..."
        )
    }
}

@Composable
private fun Error(message: String = "Error", close: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
    ) {
        Text("Произошла ошибка")
        Text(
            textAlign = TextAlign.Center,
            text = message
        )
        Button(
            onClick = { close() },
            border = BorderStroke(1.dp, Color.Black),
        ) {
            Text("Закрыть")
        }
    }
}