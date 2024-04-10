package ru.directum.maestro.android.screen.utils

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.net.Uri.*
import android.os.Environment
import android.provider.Browser
import android.util.Log
import android.webkit.CookieManager
import android.webkit.URLUtil
import android.webkit.WebView
import android.widget.Toast
import androidx.core.content.ContextCompat
import ru.directum.maestro.android.R
import java.io.DataOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLDecoder


class DownloadHelper {

    private fun downloadByReadableByteChannel(
        context: Context, url: String, userAgent: String, contentDisposition: String,
        mimeType: String, contentLength: Long
    ) {
        Log.d("stdout", "DownloadByReadableByteChannel url $url")
        val clearURL = url.replaceFirst("blob:", "").trim()
        val fileName = singleOutFileName(clearURL, contentDisposition, mimeType)
        Log.d("stdout", "   fileName $fileName")
        try {
            Log.d("stdout", "fileName fileName $fileName")
            val fullPath =
                "${Environment.getExternalStorageDirectory().absolutePath}/${Environment.DIRECTORY_DOWNLOADS}/$fileName"
            val file = File(fullPath)
            file.createNewFile()

            val link = URL(clearURL)
            Thread {
                try {
                    val inStream = link.openStream()
                    val outStream = FileOutputStream(fullPath)
                    val buffer = ByteArray(1024)
                    var bytesRead: Int
                    while (inStream.read(buffer).also { bytesRead = it } != -1) {
                        outStream.write(buffer, 0, bytesRead)
                    }
                    inStream.close()
                    outStream.close()
                    Log.d("stdout", "DownloadByReadableByteChannel ok")
                    Toast.makeText(context, "Downloading ${url}", Toast.LENGTH_LONG).show()
                } catch (e: IOException) {
                    Log.d("stdout", "error IO $e")
                    e.printStackTrace()
                }
            }.start()
        } catch (e: Exception) {
            Log.d("stdout", "error $e")
            e.printStackTrace()
        }
    }

    private fun downloadByManager(
        context: Context, url: String, userAgent: String, contentDisposition: String,
        mimeType: String, contentLength: Long
    ) {
        val clearURL = url.replaceFirst("blob:", "").trim()
        val fileName = singleOutFileName(clearURL, contentDisposition, mimeType)
        Log.d("stdout", "   fileName $fileName")

        val request = DownloadManager.Request(parse(clearURL))
            .setTitle(context.getString(R.string.download_document_title, fileName))
            .addRequestHeader("User-Agent", userAgent)
            .setMimeType(mimeType)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

        try {
            val cookies: String = CookieManager.getInstance().getCookie(URL(url).host)
            request.addRequestHeader("Cookie", cookies)
        } catch (e: Exception) {
            Log.e("stdout", "getCookie Error ${e.message}")
        }

        val downloadManager: DownloadManager? =
            context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
        val id = downloadManager?.enqueue(request)

        Log.d("stdout", "   id ${id}")

        Toast.makeText(context, "Downloading ${fileName}", Toast.LENGTH_LONG).show()
        Log.d("stdout", "StartDownloadManager OK")

    }

    private fun downloadByURLConnection(
        context: Context, url: String, userAgent: String, contentDisposition: String,
        mimeType: String, contentLength: Long
    ) {
        Thread {
            val clearURL = url.replaceFirst("blob:", "").trim()
            val fileName = singleOutFileName(clearURL, contentDisposition, mimeType)
            Log.d("stdout", "   fileName $fileName")
            val filepath: String = Environment.getDataDirectory().path + "/" + fileName
            Log.d("stdout", "filepath: $filepath")

            val url2 = URL(clearURL)
            Log.d("stdout", "url: $url2")
            var cookies = ""
            try {
                cookies = CookieManager.getInstance().getCookie(URL(url).host)
            } catch (e: Exception) {
                Log.e("stdout", "getCookie Error ${e.message}")
            }

            val con: HttpURLConnection = url2.openConnection() as HttpURLConnection
            con.requestMethod = "POST"
            con.setRequestProperty("Cookie", cookies)
            con.setRequestProperty("User-Agent", userAgent)
            con.doInput = true
            con.doOutput = true
            con.connect()

            Log.d("stdout", "ResponseCode: ${con.responseCode}")
            Log.d("stdout", "ResponseMessage: ${con.responseMessage}")

            val output = DataOutputStream(con.outputStream)
            output.writeBytes("playload")
            output.close();

            val stream = con.inputStream

            Log.d("stdout", "InputStream OK")
            val fos = FileOutputStream(File(filepath))

            Log.d("stdout", "fos OK")
            val buffer = ByteArray(1024)
            var len: Int

            while ((stream.read(buffer).also { len = it }) > 0) {
                fos.write(buffer, 0, len)
            }

            Log.d("stdout", "len ${len}")

            stream.close()
            fos.close()

            val fileExists = File(filepath).exists()
            Log.d("stdout", "Exists  $fileExists")
            Log.d("stdout", "StartHttpURLConnection OK")
        }.start()
    }

    fun singleOutFileName(
        url: String,
        contentDisposition: String,
        mimeType: String
    ): String {
        val utf8FileNameConst = "filename*=UTF-8''"
        val isExistUTF8FileNameInContent =
            contentDisposition.contains(utf8FileNameConst, ignoreCase = true)
        if (isExistUTF8FileNameInContent) {
            val findName = URLDecoder.decode(
                contentDisposition.substring(
                    contentDisposition.indexOf(utf8FileNameConst) + utf8FileNameConst.length
                ), "UTF-8"
            )
            return if (findName.contains(";"))
                findName.substring(0, findName.indexOf(";"))
            else
                findName
        }

        val fileNameConst = "filename=\""
        val isExistFileNameInContent = contentDisposition.contains(fileNameConst, ignoreCase = true)
        if (isExistFileNameInContent) {
            val findName = URLDecoder.decode(
                contentDisposition.substring(
                    contentDisposition.indexOf(fileNameConst) + fileNameConst.length
                ), "UTF-8"
            )
            return if (findName.contains("\""))
                findName.substring(0, findName.indexOf("\""))
            else
                findName
        }

        return URLUtil.guessFileName(url, contentDisposition, mimeType)
    }

    companion object {
        fun download(
            context: Context, browser: WebView,
            url: String, userAgent: String, contentDisposition: String,
            mimeType: String, contentLength: Long
        ) {
            Log.d("stdout", "download: $url")
            Log.d(
                "stdout", "   userAgent: $userAgent mimetype: $mimeType " +
                        "contentDisposition: $contentDisposition contentLength: $contentLength"
            )

            val downloadHelper = DownloadHelper()
            if (url.contains("blob:")) {
                browser.loadUrl(
                    JavaScriptInterface.getBase64StringFromBlobUrl(
                        url, downloadHelper.singleOutFileName(
                            url,
                            contentDisposition,
                            mimeType
                        )
                    )
                )
            } else
                downloadHelper.downloadByURLConnection(
                    context,
                    url,
                    userAgent,
                    contentDisposition,
                    mimeType,
                    contentLength
                )
        }
    }
}