package com.rom.garagely.ui.productModule.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.rom.garagely.common.PreferencesManager
import com.rom.garagely.constant.Constant.BRAND
import com.rom.garagely.constant.SharedPreferenceKeys
import com.rom.garagely.model.Brand
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BrandListViewModel @Inject constructor(
    private val dp: FirebaseFirestore
) : ViewModel() {

    private val _brands = MutableStateFlow<List<Brand>>(listOf())
    val brands = _brands.asStateFlow()


    init {
        getBrand()
    }

    fun getBrand() {
        viewModelScope.launch(Dispatchers.Default) {
            dp.collection(BRAND).whereEqualTo(
                "account_id",
                PreferencesManager.instance.get(SharedPreferenceKeys.USER_ID)
            )
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        _brands.value = it.result.toObjects(Brand::class.java)
                    }
                }
        }
    }
}