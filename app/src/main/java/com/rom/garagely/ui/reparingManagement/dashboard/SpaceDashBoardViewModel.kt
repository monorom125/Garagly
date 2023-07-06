package com.rom.garagely.ui.reparingManagement.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.rom.garagely.common.PreferencesManager
import com.rom.garagely.constant.Constant.CLIENT
import com.rom.garagely.constant.Constant.ORDER
import com.rom.garagely.constant.Constant.SELL
import com.rom.garagely.constant.SharedPreferenceKeys
import com.rom.garagely.constant.enitity.ClientEntity
import com.rom.garagely.model.Client
import com.rom.garagely.model.Order
import com.rom.garagely.model.Sell
import com.rom.garagely.util.delete
import com.rom.garagely.util.isNull
import com.rom.garagely.util.upsert
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpaceDashBoardViewModel @Inject constructor(
    private val db: FirebaseFirestore,
    private val storage: FirebaseStorage
) : ViewModel() {

    var clients = listOf<Client>()

    private var _client = MutableLiveData<List<Client>>()
    val clientsLiveData: LiveData<List<Client>> get() = _client
    private var _sell = MutableLiveData<Sell?>()

    var orders = MutableLiveData<List<Order>>()

    val sellLiveData: LiveData<Sell?> get() = _sell

    var sell: Sell? = null
        set(value) {
            field = value
            _sell.postValue(field)
            getOrders(field)
        }

    init {
        getClient()
        getSell()
    }


    fun closeSell(sell: Sell, isDelete: Boolean = false) {

        viewModelScope.launch {
            if (isDelete) {
                db.delete(sell, onSuccess = {
                    getSell()
                }) {

                }
            } else {
                db.upsert(sell, onSuccess = {
                    getSell()
                }) {
                }
            }

        }
    }

    private fun getOrders(sell: Sell?) {
        if (sell.isNull()) {
            orders.value = listOf()
            return
        }
        viewModelScope.launch {
            db.collection(ORDER).whereEqualTo("sell_id", sell!!.id)
                .get().addOnCompleteListener {
                    if (it.isSuccessful) {
                        orders.value = it.result.toObjects(Order::class.java)
                    }
                }
        }
    }

    private fun getSell() {
        viewModelScope.launch {
            db.collection(SELL).whereNotEqualTo("status", Sell.Status.Done.name)
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        sell = it.result.toObjects(Sell::class.java).firstOrNull()
                        getOrders(sell)
                        _sell.postValue(sell)
                    }
                }
        }

    }

    fun createSell(sell: Sell, onComplete: ((Sell) -> Unit)? = null) {
        viewModelScope.launch {
            db.upsert(sell, onSuccess = {
                this@SpaceDashBoardViewModel.sell = sell
                onComplete?.invoke(sell)
            }) {
            }
        }
    }

    fun upsertOrder(order: Order, completion: ((Order) -> Unit)? = null) {
        viewModelScope.launch {
            db.upsert(order, onSuccess = {
                getOrders(sell!!)
            }) {

            }
        }
    }

    fun deletedOrder(order: Order) {
        viewModelScope.launch {
            db.delete(order, onSuccess = {
                getOrders(sell)
            }) {

            }
        }
    }

    fun getClient(isPost: Boolean = false) {
        viewModelScope.launch(Dispatchers.Default) {
            db.collection(CLIENT)
                .whereEqualTo(
                    ClientEntity.account_id,
                    PreferencesManager.instance.get(SharedPreferenceKeys.USER_ID)
                )
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        clients = it.result.toObjects(Client::class.java)
                        if (isPost) {
                            _client.postValue(clients)
                        }
                    }
                }
        }
    }

    fun clearProduct(unPaidOrders: List<Order>) {
        viewModelScope.launch(Dispatchers.IO) {
            unPaidOrders.forEach {
                db.delete(it, onSuccess = {}) {
                }
            }
            getOrders(sell!!)
        }
    }
}