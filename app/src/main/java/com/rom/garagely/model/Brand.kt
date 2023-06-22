package com.rom.garagely.model

import android.os.Parcelable
import com.rom.garagely.constant.Constant.BRAND
import kotlinx.android.parcel.Parcelize
import kotlinx.parcelize.IgnoredOnParcel
import java.util.UUID


@Parcelize
data class Brand(
    override var id : String,
    var name : String,
    var product_id : MutableList<String> = mutableListOf(),
    var image : String? = null,
    var account_id : String? = null
) : Parcelable, BaseModel() {

    constructor() : this(id = UUID.randomUUID().toString(), name = "")

    @IgnoredOnParcel
    override val pathName: String
        get() =  BRAND


}
