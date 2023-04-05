package com.rom.garagely.helper

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.rom.garagely.constant.Constant.SPACE_COLLECTION
import com.rom.garagely.constant.Constant.USER_COLLECTION

class FirebaseDB {
    private val usersCollectionRef = Firebase.firestore.collection(USER_COLLECTION)
    private val specCollectionRef = Firebase.firestore.collection(SPACE_COLLECTION)
    private val firebaseStorage = Firebase.storage.reference

    val userUid = FirebaseAuth.getInstance().currentUser?.uid
}