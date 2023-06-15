package com.rom.garagely.ui.productModule.Product

import androidx.annotation.StringRes
import com.rom.garagely.R

data class Header(
    @StringRes
    val name: Int,
    val weight: Float,
    val filterType: String = "",
    var isFilter: Boolean = false,
    var orderBy: OrderBy = OrderBy.None
){
    enum class OrderBy {
        None,
        Desc,
        Asc;

        val iconResource: Int
            get() = when (this) {
                None -> R.drawable.ic_updown_ui
                Desc -> R.drawable.ic_updown_right_ui
                Asc -> R.drawable.ic_updown_left_ui
            }

        val queryString: String
            get() = when (this) {
                None -> ""
                Desc -> "DESC"
                Asc -> "ASC"
            }

        fun next(): OrderBy {
            return when(this) {
                None -> Desc
                Desc -> Asc
                Asc -> None
            }
        }
    }
}
