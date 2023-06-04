package com.rom.garagely.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Vehicle(
    val name: String? = null,
    val car_id : String? = null,
    val tableId: String? = null,
    val spaceId: String? = null,
    val status : Status = Status.OPERATING,
    val cashId : String? = null,
    val image  : String? = null
) : Parcelable, BaseModel(){

    constructor(): this(name = "", tableId = null, spaceId = null, cashId = null )

    enum class Status{
        OPERATING,
        WAITING,
        DONE,
    }
}
