package com.rom.garagely.model

import android.os.Parcelable
import com.rom.garagely.constant.Constant.PRODUCT
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.util.UUID

@Parcelize
data class Car(
    var id: String,
    var name: String? = null,
    var price: Double = 0.0,
    var brand: String? = null,
    val model: String? = null,
    var status: Status = Status.OPERATING,
    var keys: MutableList<Key> = mutableListOf(),
    var image: String? = null,
    var quantity: Int = 0,
    var account_id: String? = null
) : Parcelable, BaseModel() {

    constructor() : this(id = UUID.randomUUID().toString(), name = "", brand = null, model = null)

    enum class Status {
        OPERATING,
        WAITING,
        DONE,
    }

    override val pathName: String
        get() = PRODUCT
}

@Parcelize
data class Key(val name: String?, val price: Double?) : Parcelable{
    constructor() : this(null,null)
}
