package com.stori.challenge.domain.usecases

import android.graphics.Bitmap
import com.google.firebase.storage.FirebaseStorage
import com.stori.challenge.util.constants.FirestoreUserConstants.COLLECTION_IMAGES
import com.stori.challenge.util.constants.FirestoreUserConstants.PREFIX_ID_PICTURE
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream

typealias PhotoUrl = String

class UploadDocumentImageUseCase {
    private val firebaseStorage: FirebaseStorage by lazy { FirebaseStorage.getInstance() }

    suspend fun execute(uid: String, documentImageBitmap: Bitmap): Result<PhotoUrl> {
        return try {
            val imageRef = firebaseStorage
                .reference
                .child(
                    "$COLLECTION_IMAGES/$PREFIX_ID_PICTURE$uid",
                )

            val outputStream = ByteArrayOutputStream()
            documentImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            val data = outputStream.toByteArray()

            imageRef.putBytes(data).await()

            val imageUrl = imageRef.downloadUrl.await().toString()
            Result.success(imageUrl)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
