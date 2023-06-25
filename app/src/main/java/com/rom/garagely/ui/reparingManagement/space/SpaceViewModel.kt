package com.rom.garagely.ui.reparingManagement.space

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.rom.garagely.common.PreferencesManager
import com.rom.garagely.constant.Constant.BRAND
import com.rom.garagely.constant.Constant.PRODUCT
import com.rom.garagely.constant.SharedPreferenceKeys
import com.rom.garagely.model.Brand
import com.rom.garagely.model.Car
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import javax.inject.Inject

@HiltViewModel
class SpaceViewModel @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth

) : ViewModel() {


    private val _brand = MutableLiveData<List<Brand>>()
    val brand: LiveData<List<Brand>> get() = _brand

    private val _car = MutableLiveData<List<Car>>()
    val car: LiveData<List<Car>> get() = _car

    init {
        getSpace()
        getCar()
    }

    private fun getSpace() {
        viewModelScope.launch {
            val userId = firebaseAuth.currentUser?.uid ?: ""
            firestore.collection(BRAND)
                .whereEqualTo("account_id", userId)
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val spaces = it.result.toObjects(Brand::class.java)
                        _brand.postValue(spaces)
                    }
                }
        }
    }

    private fun getCar() {
        viewModelScope.launch(Dispatchers.Default) {
            firestore.collection(PRODUCT)
                .whereEqualTo(
                    "account_id",
                    PreferencesManager.instance.get(SharedPreferenceKeys.USER_ID)
                )
                .get()
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        _car.postValue(it.result.toObjects(Car::class.java))
                    }
                }
        }
    }
}