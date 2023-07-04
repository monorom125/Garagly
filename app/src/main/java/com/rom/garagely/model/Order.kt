package com.rom.garagely.model

import android.os.Parcelable
import com.rom.garagely.common.PreferencesManager
import com.rom.garagely.constant.Constant
import kotlinx.android.parcel.Parcelize
import java.util.UUID

@Parcelize
data class Order(
    override var id: String = UUID.randomUUID().toString(),
    var account_id: String? = null,
    var key: Key? = null,
    var product: Car? = null,
    var sell_id : String? = null,
    var qty: Int = 0,
    var status : Status = Status.Order
) : Parcelable, BaseModel() {

    override val pathName: String
        get() = Constant.ORDER


    enum class Status {
        Order, Paid
    }
}