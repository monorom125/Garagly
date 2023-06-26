package com.rom.garagely.model

import android.os.Parcelable
import android.provider.ContactsContract.CommonDataKinds.Email
import android.provider.ContactsContract.CommonDataKinds.SipAddress
import com.rom.garagely.constant.Constant.CLIENT
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.util.Date
import java.util.UUID

@Parcelize
data class Client(
    override var id: String = UUID.randomUUID().toString(),
    var account_id: String? = null,
    var first_name: String? = null,
    var last_name: String? = null,
    var image: String? = null,
    var gender: Gender = Gender.Male,
    var phone_number: String,
    var email: String? = null,
    var date: Date = Date(),
    var comment: String? = null,
    var country: String? = null,
    var address: String? = null,
) : BaseModel(), Parcelable {


    constructor() : this(id = UUID.randomUUID().toString(), phone_number = "")

    override val pathName: String
        get() = CLIENT


    enum class Gender {
        Male, Female, Other
    }
}
