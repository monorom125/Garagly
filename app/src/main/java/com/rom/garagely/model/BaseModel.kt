package com.rom.garagely.model

import android.os.Parcelable
import com.rom.garagely.util.Util.Companion.UUIDToString
import kotlinx.android.parcel.Parcelize
import kotlinx.parcelize.IgnoredOnParcel
import java.io.Serializable
import java.lang.reflect.Field
import java.util.*


abstract class BaseModel :  Cloneable, Parcelable {

   abstract val pathName : String
}


