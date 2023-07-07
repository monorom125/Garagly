package com.rom.garagely.model

import android.os.Parcelable
import com.google.firebase.firestore.Exclude
import com.rom.garagely.constant.Constant
import com.rom.garagely.util.isNull
import kotlinx.android.parcel.Parcelize
import kotlinx.parcelize.IgnoredOnParcel
import java.util.UUID

@Parcelize
data class Order(
    override var id: String = UUID.randomUUID().toString(),
    var account_id: String? = null,
    var key: Key? = null,
    var product: Car? = null,
    var sell_id: String? = null,
    var qty: Int = 0,
    var status: Status = Status.Order
) : Parcelable, BaseModel() {

    override val pathName: String
        get() = Constant.ORDER


    fun isTheSame(order: Order): Boolean {
        return this.account_id == order.account_id && this.product?.id == order.product?.id && this.status == order.status
    }


    val isEdible: Boolean
        @Exclude get() {
            return this.status == Status.Order
        }


//    @IgnoredOnParcel
//    val discountAmount: Double
//        get() {
//            return if (product!!.discount.isNull()) {
//                product!!.productPriceWithTax * (product!!.discount?.discount_amount!! / 100)
//            } else {
//                0.00
//            }
//        }

    val totalAmount: Double
        get() = if (product?.productPriceWithTax.isNull()) {
            0.00
        } else {
            (qty * product!!.productPriceWithTax)
        }
}

enum class Status {
    Order, Paid
}