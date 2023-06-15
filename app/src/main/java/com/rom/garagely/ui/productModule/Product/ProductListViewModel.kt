package com.rom.garagely.ui.productModule.Product

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.rom.garagely.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor() : ViewModel() {

    var type :Type = Type.Product
        set(value) {
            headers.clear()
            field = value
            headers.addAll(value.header)
        }
    var headers = mutableStateListOf<Header>()

    init {
        headers.addAll(Type.Product.header)
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