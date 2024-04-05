package ru.directum.maestro.android.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.directum.maestro.Presentation.Model.InputURLViewModel
import ru.directum.maestro.android.R
import ru.directum.maestro.android.screen.view.FavoriteLinkGroup


@Composable
fun InputURLScreen(
    showWebView: (url: String) -> Unit
) {
    val viewModel = remember { InputURLViewModel() }
    val state by viewModel.state.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.3f)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.7f)
        ) {
            when {
                state == null -> {}
                state!!.success -> {
                    showWebView(state!!.url)
                }

                state!!.loading -> ProgressIndicator()
                state!!.error -> Error(
                    message = state!!.errorMessage,
                    close = viewModel::resetError
                )

                else -> InputEditBox(state!!.url, viewModel::connect)
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, device = "id:Nexus S")
@Composable
private fun InputEditBoxP() {
    InputEditBox("google.com", {})
}

@Composable
private fun InputEditBox(
    url: String, connectToUrl: (url: String) -> Unit
) {
    var text by rememberSaveable { mutableStateOf(url) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .wrapContentSize(Alignment.Center)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center),
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_menu),
                contentDescription = "logo_menu"
            )
        }
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
            keyboardActions = KeyboardActions(
                onGo = { connectToUrl(text) }
            ),
            onValueChange = {
                text = it
            },
            label = { Text("Введите адрес сервера:") },
            trailingIcon = {
                Icon(
                    Icons.Default.Clear,
                    contentDescription = "clear text",
                    modifier = Modifier
                        .clickable {
                            text = ""
                        }
                )
            }
        )
        Button(
            onClick = {
                connectToUrl(text)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.btn_next))
        }

        Spacer(modifier = Modifier.height(16.dp))
        FavoriteLinkGroup.FavoriteRows(connectToUrl)
    }
}

@Composable
private fun ProgressIndicator() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize()
    ) {

        Image(
            painter = painterResource(id = R.drawable.logo_menu),
            contentDescription = "splash",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
        Text(
            textAlign = TextAlign.Center,
            text = "Подключение...",
            modifier = Modifier.align(Alignment.CenterHorizontally)
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