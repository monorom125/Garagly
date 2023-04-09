package com.rom.garagely.ui.reparingManagement.space

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.rom.garagely.model.GarageSpace
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpaceViewModel @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth

) : ViewModel() {


    private val _spaces = MutableLiveData<List<GarageSpace>>()
    val space: LiveData<List<GarageSpace>> get() = _spaces

    init {
        getSpace()
    }
    private fun getSpace() {
        viewModelScope.launch {
            val userId = firebaseAuth.currentUser?.uid ?: ""
            firestore.collection("rest_space")
                .whereEqualTo("user_id", userId)
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val spaces = it.result.toObjects(GarageSpace::class.java)
                        _spaces.postValue(spaces)
                    }
                }
        }
    }
}