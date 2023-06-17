package com.rom.garagely.ui.productModule.Product

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.rom.garagely.R
import com.rom.garagely.common.PreferencesManager
import com.rom.garagely.constant.Constant
import com.rom.garagely.constant.Constant.PRODUCT
import com.rom.garagely.constant.SharedPreferenceKeys
import com.rom.garagely.model.Car
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _products = MutableStateFlow<List<Car>>(listOf())
    val product = _products.asStateFlow()


    var type: Type = Type.Product
        set(value) {
            headers.clear()
            field = value
            headers.addAll(value.header)
        }
    var headers = mutableStateListOf<Header>()

    init {
        headers.addAll(Type.Product.header)
        getProducts()
    }

     fun getProducts() {
        viewModelScope.launch {
            firestore.collection(PRODUCT)
                .whereEqualTo("account_id", preferencesManager.get(SharedPreferenceKeys.USER_ID))
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        _products.value = it.result.toObjects(Car::class.java)
                    }
                }
        }
    }

    enum class Type {
        Product, Discount, Tax;

        val header: List<Header>
            get() = when (this) {
                Product -> listOf(
                    Header(
                        name = R.string.name,
                        weight = 1f
                    ),
                    Header(
                        name = R.string.brand,
                        weight = 1f
                    ),
                    Header(
                        name = R.string.price,
                        weight = 1f
                    ),
                    Header(
                        name = R.string.qaunity,
                        weight = 1f
                    )
                )

                else -> listOf()
            }
    }
}