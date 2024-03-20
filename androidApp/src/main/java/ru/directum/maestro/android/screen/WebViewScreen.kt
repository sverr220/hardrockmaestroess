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
                        val uri = Uri.parse(url.replaceFirst("blob:", "").trim());

                        val fileName = "test.pdf";

                        val request = DownloadManager.Request(uri);

                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        request.addRequestHeader("User-Agent", userAgent);
                        val downloadManager: DownloadManager? =
                            context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager?;
                        downloadManager?.enqueue(request);
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