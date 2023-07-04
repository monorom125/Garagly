package com.rom.garagely.ui.productModule.tax

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.rom.garagely.model.Tax
import com.rom.garagely.util.upsertOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaxDetailViewModel @Inject constructor(
    private val db : FirebaseFirestore
) : ViewModel() {

    private val _tax = MutableStateFlow<Tax?>(null)
    val tax = _tax.asStateFlow()

    private val _createProductStat = MutableStateFlow(false)
    val result = _createProductStat.asStateFlow()

    fun createProduct(tax: Tax) {
        viewModelScope.launch(Dispatchers.Default) {
            db.upsertOrder(tax, onSuccess = {
                _createProductStat.value = it
            }) {

            }
        }
    }

    fun setTax(car: Tax?) {
        _tax.value =car
    }
}