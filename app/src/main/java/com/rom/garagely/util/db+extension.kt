package com.rom.garagely.util

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.rom.garagely.constant.Constant
import com.rom.garagely.model.BaseModel

suspend fun FirebaseFirestore.insert(model: BaseModel, onSuccess : (Boolean)-> Unit){

    this.collection(model.pathName).add(model).addOnCompleteListener {
        onSuccess.invoke(it.isSuccessful)
    }
}