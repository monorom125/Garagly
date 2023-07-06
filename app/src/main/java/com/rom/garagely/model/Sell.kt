package com.rom.garagely.model

import com.rom.garagely.constant.Constant.SELL
import kotlinx.android.parcel.Parcelize
import java.util.Date
import android.os.Parcelable
import com.google.firebase.firestore.Exclude
import kotlinx.parcelize.IgnoredOnParcel
import java.util.UUID

@Parcelize
data class Sell(
    override var id: String = UUID.randomUUID().toString(),
    var account_id: String? = null,
    var date: Date = Date(),
    var client: Client? = null,
    var status: Status = Status.UnPaid
) : Parcelable, BaseModel() {

    override val pathName: String
        get() = SELL

    @IgnoredOnParcel
    @Exclude
    var orders  = arrayListOf<Order>()

    enum class Status {
        UnPaid, Paid, Done
    }
}
