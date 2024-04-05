package ru.directum.maestro.android.screen.utils

import android.net.Uri
import android.util.Log
import android.webkit.JsResult
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView

class CustomWebChromeClient(private val progressChangedValue: (value: Int) -> Unit) :
    WebChromeClient() {

    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        super.onProgressChanged(view, newProgress)
        progressChangedValue(newProgress)
    }

    override fun onShowFileChooser(
        webView: WebView?,
        filePathCallback: ValueCallback<Array<Uri>>?,
        fileChooserParams: FileChooserParams?
    ): Boolean {
        Log.d("stdout", "onShowFileChooser")
        return super.onShowFileChooser(webView, filePathCallback, fileChooserParams)
    }

    override fun onJsAlert(
        view: WebView?,
        url: String?,
        message: String?,
        result: JsResult?
    ): Boolean {
        Log.d("stdout", "onJsAlert $url")
        return super.onJsAlert(view, url, message, result)
    }
}