package com.rom.garagely.ui.loginByPin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.rom.garagely.constant.Constant.USER_COLLECTION
import com.rom.garagely.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginPinCodeViewModel @Inject constructor(
    private val db: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _isEnterPinSuccess = MutableStateFlow<Boolean>(false)
    val isEnterPinSuccess = _isEnterPinSuccess.asStateFlow()

    var user : User? = null

    fun checkPinCode(pinCode: String) {
        val userId = firebaseAuth.currentUser?.uid
        viewModelScope.launch {
            db.collection(USER_COLLECTION)
                .whereEqualTo("id", userId)
                .get()
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        val user = it.result.toObjects(User::class.java).firstOrNull()
                        Log.d("User","${it.result}" )
                        this@LoginPinCodeViewModel.user = user
                        _isEnterPinSuccess.value = user?.pincode == pinCode
                    }
                }
        }
    }
}