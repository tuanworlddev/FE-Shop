package com.dacs3.shop.repository

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UploadImageRepository @Inject constructor() {
    private val storageRef = FirebaseStorage.getInstance().reference
    suspend fun uploadImage(imageUri: Uri): String {
        val imageRef = storageRef.child("images/${System.currentTimeMillis()}.jpg")
        imageRef.putFile(imageUri).await()
        return imageRef.downloadUrl.await().toString()
    }

    suspend fun deleteImage(imageUrl: String) {
        val imageRef = storageRef.storage.getReferenceFromUrl(imageUrl)
        imageRef.delete().await()
    }
}