package com.rom.garagely.ui.productModule.tax

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.rom.garagely.R
import com.rom.garagely.common.PreferencesManager
import com.rom.garagely.constant.Constant
import com.rom.garagely.constant.SharedPreferenceKeys
import com.rom.garagely.model.Discount
import com.rom.garagely.model.Tax
import com.rom.garagely.ui.productModule.Product.Header
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaxListViewModel @Inject constructor(
    private val dp: FirebaseFirestore
) : ViewModel() {

    private val _taxs = MutableStateFlow<List<Tax>>(listOf())
    val tax = _taxs.asStateFlow()

    val headers: List<Header>
        get() = listOf(
            Header(name = R.string.name, weight = 1f),
            Header(name = R.string.percent, weight = 1f),

            )


    init {
        getDiscounts()
    }

    private fun getDiscounts() {
        viewModelScope.launch(Dispatchers.Default) {
            dp.collection(Constant.TAX).whereEqualTo(
                "account_id",
                PreferencesManager.instance.get(SharedPreferenceKeys.USER_ID)
            )
                .get().addOnCompleteListener {
                    if (it.isSuccessful) {
                        _taxs.value = it.result.toObjects(Tax::class.java)
                    }
                }
        }
    }
}