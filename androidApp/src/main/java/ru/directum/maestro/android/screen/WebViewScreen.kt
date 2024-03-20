package ru.directum.maestro.android.screen

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen(url: String, openSetting: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan),
    ) {
        AndroidView(
            factory = { context ->
                return@AndroidView WebView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    settings.javaScriptEnabled = true
                    webViewClient = WebViewClient()

                    settings.loadWithOverviewMode = true
                    settings.useWideViewPort = true
                    settings.cacheMode = WebSettings.LOAD_NO_CACHE
                    settings.setSupportZoom(false)
                }
            }, update = {
                it.loadUrl(url)
            })
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WebViewScreen(url = "google.com") {
        
    }
}