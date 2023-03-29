package com.rom.garagely.util

import java.util.*

class Util {
    companion object{
        fun UUIDToString(): String {
            return UUID.randomUUID().toString().lowercase()
        }
    }
}