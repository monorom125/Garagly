package com.rom.garagely.model

import kotlinx.parcelize.Parcelize

@Parcelize
data class GarageSpace(
    val name : String,
    val accountId : String? = null,
) : BaseModel()