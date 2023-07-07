package com.rom.garagely.model

import android.os.Parcelable
import com.rom.garagely.constant.Constant.PRODUCT
import com.rom.garagely.util.isNull
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.io.Serializable
import java.util.UUID

@Parcelize
data class Car(
    override var id: String,
    var name: String? = null,
    var price: Double = 0.0,
    var brand: Brand? = null,
    var vat: Tax? = null,
    var discount: Discount? = null,
    var info: String? = null,
    var status: Status = Status.Sell,
    var keys: MutableList<Key> = mutableListOf(),
    var image: String? = null,
    var quantity: Int = 0,
    var account_id: String? = null
) : Parcelable, BaseModel() {

    constructor() : this(id = UUID.randomUUID().toString(), name = "", brand = null, vat = null)

    enum class Status {
        Sell,
        Rent,
        DONE,
    }

    override val pathName: String
        get() = PRODUCT

    val productPriceWithTax : Double
        get() {
            return if(vat.isNull()){
                price
            }
            else{
                price + ((price * vat?.tax_percent!!)/100)
            }
        }
}

@Parcelize
data class Key(val id: String, var name: String, var price: Double) : Parcelable{
    constructor() : this(id = UUID.randomUUID().toString(),"",0.00)
}
