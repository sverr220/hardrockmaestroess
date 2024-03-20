package ru.directum.maestro.android.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.*

sealed class Navigation(val route: String) {
    object InputURL : Navigation("inputUrl")
    object WebView : Navigation("webView")
    object Profile : Navigation("profile")
    object Setting : Navigation("setting")
    object Pin : Navigation("pin")
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var urlUiState = remember { String() }
    NavHost(
        navController = navController,
        startDestination = Navigation.InputURL.route,
        builder = {
            composable(Navigation.InputURL.route) {
                InputURLScreen(
                    showWebView = { url ->
                        urlUiState = url
                        navController.navigate(Navigation.WebView.route) },
                    showProfileScreen = {
                        navController.navigate(Navigation.Profile.route)
                    },
                    showPinCodeScreen = {
                        navController.navigate(Navigation.Pin.route)
                    }
                )
            }

            composable(Navigation.Profile.route) {
                ProfileScreen()
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
                WebViewScreen(urlUiState,
                        openSetting = {navController.navigate(Navigation.Setting.route)} )
            }
        })
}