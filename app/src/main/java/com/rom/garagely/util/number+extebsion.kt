package com.rom.garagely.util

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale


fun Double.percentToValue(value: Number): Number {
    return this.toDouble() * (value.toDouble() / 100)
}

val Number?.display: String
    get() {
        if (this != null) {
            val formatSymbols = DecimalFormatSymbols(Locale.US)
            formatSymbols.decimalSeparator = '.'
            return DecimalFormat("#.##", formatSymbols).format(this)
        }
        return ""
    }