package com.rom.garagely.model

import kotlinx.android.parcel.Parcelize
import android.os.Parcelable
import com.rom.garagely.constant.Constant.TAX
import java.util.UUID

@Parcelize
data class Tax(
    override var id: String = UUID.randomUUID().toString(),
    var account_id: String? = null,
    var name: String? = null,
    var tax_percent: Int? = null,
) : Parcelable, BaseModel() {

    override val pathName: String
        get() = TAX

}
