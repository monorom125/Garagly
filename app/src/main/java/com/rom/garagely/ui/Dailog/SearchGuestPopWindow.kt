package com.rom.garagely.ui.Dailog

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.rom.garagely.R
import com.rom.garagely.common.init
import com.rom.garagely.databinding.SearchGuestLayoutBinding
import com.rom.garagely.model.Client

class SearchGuestPopWindow (context: Context, width: Int, height: Int = ViewGroup.LayoutParams.WRAP_CONTENT) : PopupWindow(width, height) {

    var delegate: Delegate? = null

    private val binding = SearchGuestLayoutBinding.inflate(LayoutInflater.from(context))
    private val adapter = SelectCustomerAdapter(object : SelectCustomerAdapter.Delegate {
        override fun onCustomerSelected(customer: Client) {
            delegate?.onContactSelected(customer)
        }
    })
    private var customers = listOf<Client>()

    var isSearchAble: Boolean = false
    set(value) {
        field = value
        binding.textViewTitle.isVisible = value
        binding.editTextSearch.isVisible = value
    }

    init {
        contentView = binding.root
        setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.backgrund_white_round_8dp))
        elevation = 10f
        isOutsideTouchable = true

        binding.recyclerViewGuest.init(adapter)
        binding.textViewAddGuest.setOnClickListener {
            delegate?.onAddGuest()
        }

    }

    override fun dismiss() {
            super.dismiss()
    }

//    fun filter(query: String) {
//        adapter.filter.filter(query)
//    }

    fun setData(customers: List<Client>) {
        this.customers = customers
        adapter.set(customers)
    }


    interface Delegate {
        fun onContactSelected(customer: Client)
        fun onAddGuest()
    }
}