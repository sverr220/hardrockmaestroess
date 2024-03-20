package ru.directum.maestro.android.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.*
import androidx.compose.ui.unit.*
import ru.directum.maestro.android.screen.view.NumberBoard
import ru.directum.maestro.android.screen.view.PinDots


@Preview(showBackground = true,
)
@Composable
fun PinScreen(success: () -> Unit = {}
) {
    val maxPinSize = 4
    val pin = remember {
        mutableStateListOf<String>()
    }
    val isPinCorrect = remember {
        mutableStateOf(false)
    }
    val useBio = remember {
        mutableStateOf(false)
    }

    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Аутентификация", fontSize = 24.sp)

            Spacer(modifier = Modifier.height(10.dp))
            Box {
                when {
                    isFullPin(pin.size, maxPinSize) && isPinCorrect.value -> Text(
                        text = "Успешный ввод Pin кода",
                        color = Color.Green
                    )

                    isFullPin(pin.size, maxPinSize) -> Text(text = "Неверный код", color = Color.Red)
                    else -> Text(text = "Введите $maxPinSize значный PIN код")
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            PinDots(numbers = pin.size, dotsCount = maxPinSize)

            Spacer(modifier = Modifier.height(10.dp))

            val activity = LocalContext.current
           /*if (canAuthenticateBiometric(activity)) {
                Text(text = "Use Touch ID",
                    color = Color(0xFF00695C),
                    modifier = Modifier.clickable {
                        useBio.value = true
                    })
                Spacer(modifier = Modifier.height(10.dp))
            }*/

            NumberBoard(
                onNumberClick = {
                    if (pin.size < maxPinSize)
                        pin.add(it)
                    else {
                        pin.clear()
                        pin.add(it)
                    }
                }
            )

            if (isFullPin(pin.size, maxPinSize)) {
                isPinCorrect.value = validate(pin = pin.toList())
            }
            if (useBio.value) {
                //UseBioMetric()
                useBio.value = false
            }
        }
    }
}

fun isFullPin(pinSize: Int, maxPinSize: Int): Boolean {
    return pinSize >= maxPinSize;
}

fun validate(pin: List<String>): Boolean {
    return pin == listOf("1", "1", "1", "1")
}
/*
fun canAuthenticateBiometric(context: Context): Boolean {
    val biometricManager = BiometricManager.from(context)
    return biometricManager.canAuthenticate(BiometricManager.Authenticators.DEVICE_CREDENTIAL) == BiometricManager.BIOMETRIC_SUCCESS
}*/
/*
@Composable
private fun UseBioMetric() {
    val activity = LocalContext.current
    val executor = ContextCompat.getMainExecutor(activity)
    LaunchedEffect(key1 = true) {
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Use Force Luke")
            .setSubtitle("Force Subtitle")
            .setDescription("Force Description")
            .build()

        val biometricPrompt = BiometricPrompt(activity as FragmentActivity, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(activity, "Authenticated Succeeded", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onAuthenticationError(
                    errorCode: Int, errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(activity, "Authenticated Error", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(activity, "Authenticated Failed", Toast.LENGTH_SHORT)
                        .show()
                }
            })

        biometricPrompt.authenticate(promptInfo)
    }
}*/
