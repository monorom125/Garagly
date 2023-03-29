package com.rom.garagely.custom.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.rom.garagely.databinding.GaraglyHeaderBarBinding

class GaraglyHeaderBar @JvmOverloads constructor(
    context: Context, attr: AttributeSet? = null, defStyle: Int = 0
) : ConstraintLayout(context, attr, defStyle){

    private val binding = GaraglyHeaderBarBinding.inflate(LayoutInflater.from(context), this, true)



    fun setGaragelyName(name : String){
        binding.tvGarageName.text = name
    }

    fun setTitle(string: String){
        binding.posTitle.text = string
    }
}