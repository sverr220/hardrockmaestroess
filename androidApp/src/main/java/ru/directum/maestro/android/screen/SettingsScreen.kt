package ru.directum.maestro.android.screen

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable

@Composable
fun SettingsScreen(back: () -> Unit) {
    var text by rememberSaveable { mutableStateOf("Text") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Label") }
    )
}