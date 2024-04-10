package ru.directum.maestro.android.screen.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.Base64
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.MimeTypeMap
import android.webkit.URLUtil
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import ru.directum.maestro.android.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URLDecoder
import java.text.DateFormat
import java.util.Date

class JavaScriptInterface(private val context: Context) {
    @JavascriptInterface
    @Throws(IOException::class)
    fun getBase64FromBlobData(base64Data: String, fileName:String) {
        convertBase64StringToPdfAndStoreIt(base64Data, fileName)
    }
    @JavascriptInterface
    @Throws(IOException::class)
    fun log(log: String) {
        Log.i("stdout", " JavascriptInterface LOG: $log")
    }

    @Throws(IOException::class)
    private fun convertBase64StringToPdfAndStoreIt(base64PDf: String, fileName:String) {
        Log.i("stdout", base64PDf)
        val notificationId = 1

        val dwldsPath = File(
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS
            ).toString() + "/" + fileName
        )
        val base64 = base64PDf.substring(base64PDf.indexOf("base64,") + "base64,".length)
        Log.i("stdout", base64)
        val pdfAsBytes = Base64.decode(base64, 0)
        FileOutputStream(dwldsPath, false).use { os ->
            os.write(pdfAsBytes)
            os.flush()
        }
        if (dwldsPath.exists()) {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            val apkURI = FileProvider.getUriForFile(
                context,
                context.applicationContext.packageName + ".provider",
                dwldsPath
            )
            intent.setDataAndType(
                apkURI,
                MimeTypeMap.getSingleton().getMimeTypeFromExtension("pdf")
            )
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            val pendingIntent = PendingIntent.getActivity(
                context,
                1,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notificationChannel =
                NotificationChannel(CHANNEL_ID, "name", NotificationManager.IMPORTANCE_LOW)
            val notification = Notification.Builder(context, CHANNEL_ID)
                .setContentText("You have got something new!")
                .setContentTitle("File downloaded")
                .setContentIntent(pendingIntent)
                .setChannelId(CHANNEL_ID)
                .setSmallIcon(R.drawable.hr_pro_icon)
                .build()
            notificationManager.createNotificationChannel(notificationChannel)
            notificationManager.notify(notificationId, notification)
        }
        Toast.makeText(context, "PDF FILE DOWNLOADED!", Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val CHANNEL_ID = "HR_PRO_CHANNEL"
        fun getBase64StringFromBlobUrl(blobUrl: String, fileName: String): String {
            Log.e("stdout", " getBase64StringFromBlobUrl : $blobUrl $fileName")
            return if (blobUrl.startsWith("blob")) {
                //val url = blobUrl.replaceFirst("blob:", "").trim()
                "javascript: var xhr = new XMLHttpRequest();" +
                        "xhr.open('GET', '$blobUrl', true);" +
                        "xhr.setRequestHeader('Content-type','application/pdf');" +
                        "xhr.responseType = 'blob';" +
                        "xhr.onload = function(e) {" +
                        "    if (this.status == 200) {" +
                        "        var blobPdf = this.response;" +
                        "        var reader = new FileReader();" +
                        "        reader.readAsDataURL(blobPdf);" +
                        "        reader.onloadend = function() {" +
                        "            base64data = reader.result;" +
                        "            Android.getBase64FromBlobData(base64data, $fileName);" +
                        "        }" +
                        "    }" +
                        "};" +
                        "xhr.send();"
            } else "javascript: console.log('It is not a Blob URL');"
        }
    }
}