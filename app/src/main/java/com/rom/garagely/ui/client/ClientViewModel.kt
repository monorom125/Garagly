package com.rom.garagely.ui.client

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.rom.garagely.model.Client
import com.rom.garagely.util.uploadImage
import com.rom.garagely.util.upsert
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientViewModel @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage

) : ViewModel() {


    private val _createClient = MutableLiveData<Client>()

    val crateClient: LiveData<Client> get() = _createClient


    fun createClient(client: Client, imageUri: Uri?) {
        viewModelScope.launch {
            imageUri?.let {
               client.image = firebaseStorage.uploadImage("product/image/${client.id}",imageUri)
            }
            firebaseFirestore.upsert(client, onSuccess = {
                _createClient.postValue(client)
            }) {
            }
        }

    }
}