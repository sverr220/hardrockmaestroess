package ru.directum.maestro.android.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.*

sealed class Navigation(val route: String) {
    data object InputURL : Navigation("inputUrl")
    data object WebView : Navigation("webView")
    data object Setting : Navigation("setting")
    data object Pin : Navigation("pin")
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var urlUiState = remember { String() }
    NavHost(
        navController = navController,
        startDestination = Navigation.InputURL.route,
        builder = {
            composable(Navigation.InputURL.route) {
                InputURLScreen(
                    showWebView = { url ->
                        urlUiState = url
                        navController.navigate(Navigation.WebView.route) }
                )
            }

            composable(Navigation.Setting.route) {
                SettingsScreen(
                    back = { navController.popBackStack() }
                )
            }

            composable(Navigation.Pin.route) {
                PinScreen(
                    success = {
                        navController.navigate(Navigation.InputURL.route)
                    }
                )
            }

            composable(Navigation.WebView.route) {
                WebViewScreen(urlUiState)
            }
        })
}