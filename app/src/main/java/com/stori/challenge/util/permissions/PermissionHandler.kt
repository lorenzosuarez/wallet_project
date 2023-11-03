package com.stori.challenge.util.permissions

import android.Manifest
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import java.io.File

class PermissionHandler(private val activity: ComponentActivity) {
    private var imageUri: Uri? = null
    private val takePictureLauncher = activity.registerForActivityResult(
        ActivityResultContracts.TakePicture(),
    ) { success ->
        if (success) {
            resultCallback?.invoke(imageUri)
        } else {
            resultCallback?.invoke(null)
        }
    }

    private var resultCallback: ((Uri?) -> Unit)? = null

    fun openCamera(result: (Uri?) -> Unit) {
        resultCallback = result
        val photoFile = File(activity.externalCacheDir, "temp_image_${System.currentTimeMillis()}.jpg")
        imageUri = FileProvider.getUriForFile(
            activity,
            "${activity.packageName}.provider",
            photoFile,
        )
        takePictureLauncher.launch(imageUri)
    }
}
