package ru.directum.maestro.android.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import ru.directum.maestro.android.R

@Composable
fun ProfileScreen() {
    Profile()
}

@Preview(showBackground = true)
@Composable
fun Profile() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
    ) {
        Text("Профиль")

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.splash),
                    contentDescription = "splash"
                )
            }
        }
        Text("Имя")
        Text("Рейтинг")
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenError() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
    ) {
        Text("При получении профиля произошла ошибка")
    }
}

