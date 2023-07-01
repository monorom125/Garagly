package com.rom.garagely.ui.reparingManagement.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.rom.garagely.common.PreferencesManager
import com.rom.garagely.constant.Constant.CLIENT
import com.rom.garagely.constant.SharedPreferenceKeys
import com.rom.garagely.constant.enitity.ClientEntity
import com.rom.garagely.model.Client
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpaceDashBoardViewModel @Inject constructor(
    private val dp: FirebaseFirestore,
    private val storage: FirebaseStorage
) : ViewModel() {

    var clients = listOf<Client>()

    private var _client = MutableLiveData<List<Client>>()
    val clientsLiveData : LiveData<List<Client>> get() = _client

    init {
        getClient()
    }
     fun getClient(isPost: Boolean = false) {
        viewModelScope.launch(Dispatchers.Default) {
            dp.collection(CLIENT)
                .whereEqualTo(
                    ClientEntity.account_id,
                    PreferencesManager.instance.get(SharedPreferenceKeys.USER_ID)
                )
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        clients = it.result.toObjects(Client::class.java)
                        if(isPost){
                            _client.postValue(clients)
                        }
                    }
                }
        }
    }
}