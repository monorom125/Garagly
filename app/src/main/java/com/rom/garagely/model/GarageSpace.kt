package com.rom.garagely.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GarageSpace(
    val id :String,
    val name : String,
    val user_id : String? = null,
) : Parcelable {

    constructor() : this("","","")
}