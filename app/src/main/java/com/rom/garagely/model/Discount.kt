package com.rom.garagely.model

import android.os.Build
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.ServerTimestamp
import com.rom.garagely.R
import com.rom.garagely.constant.Constant.DISCOUNT
import com.rom.garagely.util.isNull
import kotlinx.android.parcel.Parcelize
import java.util.Date
import java.util.UUID

@Parcelize
data class Discount(
    override var id: String = UUID.randomUUID().toString(),
    var name: String? = null,
    var account_id: String? = null,
    var discount_amount: Double? = null,
    @ServerTimestamp
    var start_time: Date? = null,
    @ServerTimestamp
    var end_time: Date? = null,
    var Discount_type: DiscountTye = DiscountTye.Product,
    var active: Boolean = false,
    var type: Type = Type.Percent
) : Parcelable, BaseModel() {

    constructor() : this(id = UUID.randomUUID().toString())

    override val pathName: String
        get() = DISCOUNT


    val status: Status
        get() {
            return if (getTheActive()) {
                Status.Active
            } else {
                Status.Expired
            }
        }

    fun getTheActive(): Boolean {
        if (end_time.isNull()) return false
        return Date().before(end_time)
    }


    enum class Status {
        Active, Expired;

        fun getStatusColor() :Int{
           return when(this){
                Active -> R.color.colorOrange
               else -> R.color.color_red
            }
        }
    }

    enum class DiscountTye {
        Product, Manual,
    }

    enum class Type {
        Percent, Amount,
    }

}
