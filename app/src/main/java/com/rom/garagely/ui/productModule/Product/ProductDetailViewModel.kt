package com.rom.garagely.ui.productModule.Product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.rom.garagely.constant.Constant.PRODUCT
import com.rom.garagely.model.Car
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val _createProductStat = MutableStateFlow(false)
    val result = _createProductStat.asStateFlow()

    fun createProduct(car: Car) {
        viewModelScope.launch {
            firestore.collection(PRODUCT).add(car).addOnCompleteListener {
                if(it.isSuccessful){
                    _createProductStat.value = true
                }
            }
                .addOnFailureListener { }


        }
    }
}