package com.rom.garagely.model

import android.os.Parcelable
import android.provider.ContactsContract.CommonDataKinds.Email
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String? ="",
    val email: String? ="",
    val name : String? = "",
    val pincode: String? = "",
    val phoneNumber: String? = "",
    val imageUrl: String? =""
): Parcelable {
    constructor():this("","","","","","")
}