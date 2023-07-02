package com.rom.garagely.util

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.rom.garagely.model.BaseModel
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.ZoneId

suspend fun FirebaseFirestore.upsert(
    model: BaseModel,
    onSuccess: (Boolean) -> Unit,
    onFailure: (String) -> Unit
) {

    this.collection(model.pathName).document(model.id)
        .set(model).addOnCompleteListener {
            onSuccess.invoke(it.isSuccessful)
        }
        .addOnFailureListener {
            onFailure.invoke(it.message ?: "Unknown Error")
        }
}

suspend fun FirebaseStorage.uploadImage(path: String, image: Uri): String {
    val result = this.reference.child(path).putFile(image).await()
    return result.storage.downloadUrl.await().toString()
}

suspend fun FirebaseFirestore.delete(
    model: BaseModel, onSuccess: (Boolean) -> Unit,
    onFailure: (String) -> Unit
) {
    this.collection(model.pathName).document(model.id).delete().addOnCompleteListener {
        onSuccess.invoke(it.isSuccessful)
    }.addOnCompleteListener {
        onFailure.invoke(it.exception?.message ?: "")
    }
}

fun Any?.isNull() = this == null


@RequiresApi(Build.VERSION_CODES.O)
fun Timestamp.toLocalDate(): LocalDate? {
    return this.toDate().toInstant()?.atZone(ZoneId.systemDefault())?.toLocalDate()
}


