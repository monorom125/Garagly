package com.rom.garagely.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.util.UUID

@Parcelize
data class Car(
    var id: String,
    var name: String? = null,
    var price: Double? = null,
    val brand: String? = null,
    val model: String? = null,
    val status: Status = Status.OPERATING,
    val keys: MutableList<Key> = mutableListOf(),
    val image: String? = null
) : Parcelable{

    constructor() : this(id = UUID.randomUUID().toString(), name = "", price = null, brand = null, model = null)

    enum class Status {
        OPERATING,
        WAITING,
        DONE,
    }
}

@Parcelize
data class Key(val name: String?, val price: Double?) : Parcelable{
    constructor() : this(null,null)
}
