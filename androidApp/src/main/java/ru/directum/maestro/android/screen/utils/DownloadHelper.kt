package ru.directum.maestro.android.screen.utils

import android.app.DownloadManager
import android.content.Context
import android.net.Uri.*
import android.os.Environment
import android.util.Log
import android.webkit.CookieManager
import android.widget.Toast
import java.io.DataOutputStream
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

class DownloadHelper {
    companion object {
        fun DownloadByManager(context: Context, url: String, userAgent: String, contentDisposition: String,
                              mimetype: String, contentLength: Long) {
            val uri = parse(url.replaceFirst("blob:", "").trim())

            val fileName = "test.pdf"

            val request = DownloadManager.Request(uri)
            request.setDescription("requested " + url + " downloading");
            request.setTitle(url)
            request.allowScanningByMediaScanner()
            request.setVisibleInDownloadsUi(true)

            val url2 = URL(url)
            val cookies: String = CookieManager.getInstance().getCookie(url2.host)
            request.addRequestHeader("Cookie", cookies)
            request.addRequestHeader("User-Agent", userAgent)
            request.setMimeType(mimetype)

            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.addRequestHeader("User-Agent", userAgent)
            val downloadManager: DownloadManager? =
                context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
            var id = downloadManager?.enqueue(request)

            Log.d("stdout", "id ${id}")
            Log.d("stdout", "url ${url}")
            Log.d("stdout", "cookies ${cookies}")
            Log.d("stdout", "userAgent ${userAgent}")
            Log.d("stdout", "mimetype ${mimetype}")
            Log.d("stdout", "contentDisposition ${contentDisposition}")
            Log.d("stdout", "contentLength ${contentLength}")

            Toast.makeText(context, "Downloading ${url}", Toast.LENGTH_LONG).show()
            Log.d("stdout", "StartDownloadManager OK")

        }

        fun DownloadByURLConnection(context: Context, url: String, userAgent: String, contentDisposition: String,
                              mimetype: String, contentLength: Long) {
            val filepath: String = Environment.getDataDirectory().absolutePath + "/fileName.pdf";
            Log.d("stdout", "filepath $filepath")

            val url2 = URL(url)
            val cookies: String = CookieManager.getInstance().getCookie(url2.host)

            val con:HttpURLConnection = url2.openConnection() as HttpURLConnection
            con.requestMethod = "POST"
            con.setRequestProperty("Cookie", cookies)
            con.setRequestProperty("User-Agent", userAgent)
            con.doInput = true
            con.doOutput = true
            con.connect()

            Log.d("stdout", "ResponseCode ${con.responseCode}")
            Log.d("stdout", "ResponseMessage ${con.responseMessage}")

            val output = DataOutputStream(con.outputStream)
            output.writeBytes("playload")
            output.close();

            val stream = con.inputStream

            Log.d("stdout", "InputStream OK")
            val fos = FileOutputStream(File(filepath))

            Log.d("stdout", "fos OK")
            val buffer = ByteArray(1024)
            var len: Int

            while ((stream.read(buffer).also { len = it }) > 0)
            {
                fos.write(buffer, 0, len)
            }

            Log.d("stdout", "len ${len}")

            stream.close()
            fos.close()

            val fileExists = File(filepath).exists()
            Log.d("stdout","Exists  $fileExists")
            Log.d("stdout", "StartHttpURLConnection OK")
        }
    }
}