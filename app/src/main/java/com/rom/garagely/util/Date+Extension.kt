package com.rom.garagely.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.*
import java.util.Date
import java.util.Locale
import java.util.TimeZone


fun Date?.formatToString(): String {
    if (this == null) return ""
    val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    simpleDateFormat.timeZone = TimeZone.getDefault()

    return simpleDateFormat.format(this)
}

fun Date?.formatToHour(): String{
    if (this == null) return ""
    val simpleDateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    simpleDateFormat.timeZone = TimeZone.getDefault()
    return simpleDateFormat.format(this)
}
@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate?.toDate(): Date? {
    if(this.isNull()) return null
    return Date.from(this!!.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
}
