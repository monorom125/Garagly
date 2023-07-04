package com.rom.garagely.ui.productModule.category

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.rom.garagely.model.Brand
import com.rom.garagely.util.delete
import com.rom.garagely.util.uploadImage
import com.rom.garagely.util.upsert
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BrandDetailViewModel@Inject constructor(
    private val firebaseStorage: FirebaseStorage,
    private val firebaseFirestore: FirebaseFirestore
) : ViewModel (){


    private val _brand  = MutableStateFlow<Brand?>(null)
    val brand = _brand.asStateFlow()
    private val _createBrand = MutableStateFlow(false)
    val result = _createBrand.asStateFlow()

    fun setBrand(brand: Brand?){
        _brand.value = brand
    }
    fun createBrand(brand: Brand, uri : Uri?, isDeleteImage: Boolean = false){
        viewModelScope.launch(Dispatchers.Default) {
            uri?.let {
                brand.image = firebaseStorage.uploadImage("brand/image/${brand.id}", uri)
            }?:{
                if(isDeleteImage){
                    brand.image = null
                }
            }
            firebaseFirestore.upsert(brand, onSuccess = {
                _brand.value = brand
                _createBrand.value = !_createBrand.value

            }){
                Log.d("ERROR : " , it)
            }
        }
    }

    fun delete(brand: Brand){
        viewModelScope.launch(Dispatchers.Default) {
            firebaseFirestore.delete(brand, onSuccess = {
                    _createBrand.value = !_createBrand.value
            }){

            }
        }
    }
}
