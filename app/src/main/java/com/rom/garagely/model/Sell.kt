package com.rom.garagely.model

import com.rom.garagely.constant.Constant.SELL
import kotlinx.android.parcel.Parcelize
import java.util.Date
import android.os.Parcelable
import java.util.UUID

@Parcelize
data class Sell(
    override var id: String = UUID.randomUUID().toString(),
    var account_id: String? = null,
    var client_id: String? = null,
    var date: Date = Date(),
    val orderCart_id: String? = null,
    val total_discount_amount: Double? = null,
    val tax : Tax? = null,
    var totalTax_amount : Double? = null,
    val total_amount : Double? = null,
) : Parcelable, BaseModel() {

    override val pathName: String
        get() = SELL
}
