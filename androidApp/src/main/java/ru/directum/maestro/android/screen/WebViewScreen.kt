package ru.directum.maestro.android.screen

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import ru.directum.maestro.Presentation.Model.WebViewViewModel
import ru.directum.maestro.android.screen.utils.CustomWebChromeClient
import ru.directum.maestro.android.screen.utils.CustomWebViewClient
import ru.directum.maestro.android.screen.utils.DownloadHelper
import java.net.URL

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen(url: String) {
    val viewModel = remember { WebViewViewModel() }
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .imePadding()
            .fillMaxSize()
            .background(Color.Cyan),
    ) {
        if (state!!.loading)
            LinearProgressIndicator(
                progress = { state!!.loadingProgressValue },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
            )

        AndroidView(
            factory = { context ->
                return@AndroidView WebView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    webViewClient = CustomWebViewClient(
                        context,
                        URL(url).host,
                        viewModel::changeLoadingState
                    )
                    webChromeClient = CustomWebChromeClient(viewModel::changeLoadingProgressValue)

                    settings.loadWithOverviewMode = true
                    settings.useWideViewPort = true
                    settings.cacheMode = WebSettings.LOAD_NO_CACHE
                    settings.setSupportZoom(false)

                    settings.javaScriptEnabled = true
                    settings.allowContentAccess = true
                    settings.domStorageEnabled = true
                    settings.allowFileAccess = true
                    settings.setSupportMultipleWindows(false)
                    settings.blockNetworkLoads = false
                    settings.blockNetworkImage = false
                    settings.databaseEnabled = true
                    settings.javaScriptCanOpenWindowsAutomatically = true

                    setDownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
                        DownloadHelper.download(
                            context,
                            url,
                            userAgent,
                            contentDisposition,
                            mimetype,
                            contentLength
                        )
                    }
                }
            }, update = {
                it.loadUrl(url)
            })
    }
}