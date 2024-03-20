package ru.directum.maestro.android.screen.view

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun NumberBoard(
    onNumberClick: (num: String) -> Unit = {}
) {
    val buttons = (1..9).toMutableList()
    buttons.addAll(mutableListOf(-1, 0, -1))
    Column {
        buttons.chunked(3).forEach { buttonRow ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                buttonRow.forEach { buttonNumber ->
                    PinButton(
                        number = buttonNumber.toString(),
                        onClick = onNumberClick,
                        visible = buttonNumber >= 0
                    )
                }
            }
        }
    }
}