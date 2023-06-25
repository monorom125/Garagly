package com.rom.garagely.ui.productModule.Product

import android.media.Image
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.rom.garagely.model.Car
import com.rom.garagely.util.uploadImage
import com.rom.garagely.util.upsert
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) : ViewModel() {

    private val _car = MutableStateFlow<Car?>(null)
    val car = _car.asStateFlow()

    private val _createProductStat = MutableStateFlow(false)
    val result = _createProductStat.asStateFlow()

    fun createProduct(car: Car, image: Uri?) {
        viewModelScope.launch(Dispatchers.IO) {
            image?.let {
                car.image = storage.uploadImage("products/image/${car.id}", image)
            }
            firestore.upsert(car, onSuccess = {
                _createProductStat.value = it

            }) {

            }
        }
    }

    fun setCar(car: Car?) {
        _car.value = car
    }
}