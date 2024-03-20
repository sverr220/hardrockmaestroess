package ru.directum.maestro.android.screen.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*

@Preview
@Composable
fun PinDotsP() {
    Row() {
        PinDots(2, 4)
    }
}

@Preview( widthDp = 150)
@Composable
fun PinDots2() {
    PinDots(2,3)
}

@Composable
fun PinDots(
    numbers: Int = 3,
    dotsCount: Int = 5
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        for (i in 0..<dotsCount)
            PinIndicator(numbers > i)
    }
}

@Composable
private fun PinIndicator(
    filled: Boolean,
) {
    Box(
        modifier = Modifier
            .padding(15.dp)
            .size(15.dp)
            .clip(CircleShape)
            .background(if (filled) Color.Black else Color.Transparent)
            .border(2.dp, Color.Black, CircleShape)
    )
}