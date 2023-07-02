package com.rom.garagely.ui.productModule.Discount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.rom.garagely.R
import com.rom.garagely.common.PreferencesManager
import com.rom.garagely.constant.Constant.DISCOUNT
import com.rom.garagely.constant.SharedPreferenceKeys
import com.rom.garagely.model.Discount
import com.rom.garagely.ui.productModule.Product.Header
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscountListViewModel @Inject constructor(
    private val db: FirebaseFirestore
) : ViewModel() {

    private val _discounts = MutableStateFlow<List<Discount>>(listOf())
    val discounts = _discounts.asStateFlow()

    val headers: List<Header>
        get() = listOf(
            Header(name = R.string.name, weight = 1f),
            Header(name = R.string.amount, weight = 1f),
            Header(name = R.string.start_date, weight = 1f),
            Header(name = R.string.end_date, weight = 1f),
            Header(name = R.string.status, weight = 1f)

        )


    init {
        getDiscounts()
    }

    private fun getDiscounts() {
        viewModelScope.launch(Dispatchers.Default) {
            db.collection(DISCOUNT).whereEqualTo(
                "account_id",
                PreferencesManager.instance.get(SharedPreferenceKeys.USER_ID)
            )
                .get().addOnCompleteListener {
                    if (it.isSuccessful) {
                        _discounts.value = it.result.toObjects(Discount::class.java)
                    }
                }
        }
    }
}