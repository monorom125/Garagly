package com.rom.garagely.custom.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.rom.garagely.databinding.GaraglyHeaderBarBinding

class GaraglyHeaderBar @JvmOverloads constructor(
    context: Context, attr: AttributeSet? = null, defStyle: Int = 0
) : ConstraintLayout(context, attr, defStyle) {

    var delegate : Delegate? = null
    private val binding = GaraglyHeaderBarBinding.inflate(LayoutInflater.from(context), this, true)


    init {
        binding.btnBack.setOnClickListener {
            isVisibleBack(false)
            delegate?.onBackClick()
        }
        binding.imageLogout.setOnClickListener {
            delegate?.onLogoutClick()
        }
    }
    fun setGaragelyName(name: String) {
        binding.tvGarageName.text = name
    }

    fun setTitle(string: String) {
        binding.posTitle.text = string
    }

    fun isVisibleBack(visible: Boolean = false){
        binding.imgBack.isVisible = visible
    }


    interface Delegate {
        fun onLogoutClick()
        fun onBackClick() {}
    }
}