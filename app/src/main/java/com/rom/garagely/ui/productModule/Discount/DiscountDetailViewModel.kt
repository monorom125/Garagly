package com.rom.garagely.ui.productModule.Discount

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.rom.garagely.model.Discount
import com.rom.garagely.util.upsertOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscountDetailViewModel @Inject constructor(
    private val firestore: FirebaseFirestore
) : ViewModel() {


    private val _discount = MutableStateFlow<Discount?>(null)
    val discount = _discount.asStateFlow()
    var onSuccess  = mutableStateOf(false)

    fun upsertDiscount(discount: Discount){
        viewModelScope.launch(Dispatchers.Default) {
            firestore.upsertOrder(discount, onSuccess = {
                onSuccess.value = !onSuccess.value

            }){

            }
        }
    }
    fun setDiscount(discount: Discount?){
        _discount.value = discount
    }
}