package com.rom.garagely.ui.productModule.Product

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.rom.garagely.constant.Constant.BRAND
import com.rom.garagely.constant.Constant.TAX
import com.rom.garagely.model.Brand
import com.rom.garagely.model.Car
import com.rom.garagely.model.Tax
import com.rom.garagely.util.uploadImage
import com.rom.garagely.util.upsertOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage,
    private val accountId: String
) : ViewModel() {

    private val _car = MutableStateFlow<Car?>(null)
    val car = _car.asStateFlow()

    private val _createProductStat = MutableStateFlow(false)
    val result = _createProductStat.asStateFlow()


    init {

        getCategory()
        getAllVat()
    }

    var category = listOf<Brand>()
    var vats = listOf<Tax>()

    fun createProduct(car: Car, image: Uri?) {
        viewModelScope.launch(Dispatchers.IO) {
            image?.let {
                car.image = storage.uploadImage("products/image/${car.id}", image)
            }
            firestore.upsertOrder(car, onSuccess = {
                _createProductStat.value = it

            }) {

            }
        }
    }

    private fun getCategory() {
        viewModelScope.launch {
            firestore.collection(BRAND)
                .whereEqualTo("account_id", accountId)
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        category = it.result.toObjects(Brand::class.java)
                    }
                }
        }
    }

    private fun getAllVat() {
        viewModelScope.launch {
            firestore.collection(TAX)
                .whereEqualTo("account_id", accountId)
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        vats = it.result.toObjects(Tax::class.java)
                    }
                }
        }
    }

    fun setCar(car: Car?) {
        _car.value = car
    }
}