package ru.directum.maestro.android

import android.content.pm.PackageManager
import android.Manifest
import android.Manifest.permission.ACCESS_NOTIFICATION_POLICY
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.READ_MEDIA_VIDEO
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import ru.directum.maestro.android.screen.MainScreen

class MainActivity : ComponentActivity() {

    private lateinit var requestMultiplePermissionsLauncher: ActivityResultLauncher<Array<String>>
    private var requestPerms = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
        requestMultiplePermissions()
        accepted()
    }

    private fun requestMultiplePermissions() {
        requestMultiplePermissionsLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                permissions.entries.forEach {
                    Toast.makeText(this, "permission: ${it.key} = ${it.value}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun accepted() {
        if(requestPerms) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                requestMultiplePermissionsLauncher.launch(arrayOf(READ_MEDIA_IMAGES, ACCESS_NOTIFICATION_POLICY))
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestMultiplePermissionsLauncher.launch(arrayOf(READ_MEDIA_IMAGES, ACCESS_NOTIFICATION_POLICY))
            } else {
                requestMultiplePermissionsLauncher.launch(arrayOf(WRITE_EXTERNAL_STORAGE))
            }
            requestPerms = false
        }
    }
}
