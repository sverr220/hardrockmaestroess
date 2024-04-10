package ru.directum.maestro.android.screen.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.util.Log
import android.webkit.SslErrorHandler
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient


class CustomWebViewClient(
    private val context: Context,
    private val host: String,
    private val changeProgressBar: (value: Boolean) -> Unit
) : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        Log.d("stdout", "shouldOverrideUrlLoading $url")
        return false
    }

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        Log.d("stdout", "new shouldOverrideUrlLoading ${request?.url}")
        /*if (request?.url?.host?.contains(host) == true)
            return super.shouldOverrideUrlLoading(view, request)

        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(request?.url.toString())))
        return true*/

        return super.shouldOverrideUrlLoading(view, request)
    }

    override fun onReceivedSslError(
        view: WebView?,
        handler: SslErrorHandler,
        error: SslError?
    ) {
        handler.proceed() // Ignore SSL certificate errors
    }

    override fun onLoadResource(view: WebView?, url: String?) {
        super.onLoadResource(view, url)
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        Log.d("stdout", "onPageStarted $url")
        super.onPageStarted(view, url, favicon)
        changeProgressBar(true)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        Log.d("stdout", "onPageFinished $url")
        super.onPageFinished(view, url)
        changeProgressBar(false)
    }

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        Log.d("stdout", "onReceivedError ${error?.errorCode} ${request?.url}")
        super.onReceivedError(view, request, error)
    }
}