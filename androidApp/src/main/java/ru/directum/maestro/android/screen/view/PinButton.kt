package ru.directum.maestro.android.screen.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*

@Preview(showBackground = false)
@Composable
fun PinButton(
    modifier: Modifier = Modifier,
    number: String = "1",
    onClick: (number: String) -> Unit = {},
    visible: Boolean = true
) {
    if (visible)
        Button(
            onClick = {
                onClick(number)
            },
            border = BorderStroke(1.dp, Color.Black),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            modifier = modifier
                .size(70.dp)
                .padding(5.dp),
            shape = RoundedCornerShape(70.dp)
        ) {
            Text(
                text = number, color = Color.Black, fontSize = 14.sp
            )
        }
    else
        Box(modifier = Modifier.size(70.dp))
}