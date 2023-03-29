package com.rom.garagely.ui.loginActiviy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.rom.garagely.common.PreferencesManager
import com.rom.garagely.constant.SharedPreferenceKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginByMailViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _resultState = MutableStateFlow(false)
    val result = _resultState.asStateFlow()
    fun loginByMail(email: String, password: String) {
        viewModelScope.launch {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    _resultState.value = it.isSuccessful
                    if(it.isSuccessful){
                        preferencesManager.store(SharedPreferenceKeys.USER_ID, firebaseAuth.currentUser?.uid)
                    }
                }
        }
    }
}