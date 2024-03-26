package ru.directum.maestro.android.screen

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context.DOWNLOAD_SERVICE
import android.net.Uri
import android.os.Environment
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import ru.directum.maestro.android.screen.utils.DownloadHelper

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen(url: String, openSetting: () -> Unit) {
    Box(
        modifier = Modifier
            .imePadding()
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
                    webViewClient = WebViewClient()
                    webChromeClient = WebChromeClient()

                    settings.loadWithOverviewMode = true
                    settings.useWideViewPort = true
                    settings.cacheMode = WebSettings.LOAD_NO_CACHE
                    settings.setSupportZoom(false)

                    settings.javaScriptEnabled = true
                    settings.allowContentAccess = true
                    settings.domStorageEnabled = true
                    settings.allowFileAccess = true
                    settings.allowFileAccessFromFileURLs = true
                    settings.allowUniversalAccessFromFileURLs = true
                    settings.setSupportMultipleWindows(true)
                    settings.blockNetworkLoads = false
                    settings.blockNetworkImage = false
                    settings.databaseEnabled = true
                    settings.javaScriptCanOpenWindowsAutomatically = true

                    setDownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
                        //DownloadHelper.DownloadByManager(context, url, userAgent, contentDisposition, mimetype, contentLength)
                        Thread {
                                DownloadHelper.DownloadByURLConnection(
                                    url = url,
                                    userAgent = userAgent
                                )
                        }.start()
                    }
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