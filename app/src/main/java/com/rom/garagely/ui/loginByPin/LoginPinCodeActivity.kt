package com.rom.garagely.ui.loginByPin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import com.rom.garagely.MainActivity
import com.rom.garagely.R
import com.rom.garagely.databinding.ActivityLoginPinCodeBinding
import com.rom.garagely.ui.base.BaseActivity

class LoginPinCodeActivity : BaseActivity<ActivityLoginPinCodeBinding>() {

    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent(context, LoginPinCodeActivity::class.java).apply {
            })
        }
    }

    private val pinViews by lazy {
        listOf(
            binding.layoutPin.imageViewPin1,
            binding.layoutPin.imageViewPin2,
            binding.layoutPin.imageViewPin3,
            binding.layoutPin.imageViewPin4,
        )
    }

    private val numPadViews by lazy {
        listOf(
            binding.layoutNumPad.button1,
            binding.layoutNumPad.button2,
            binding.layoutNumPad.button3,
            binding.layoutNumPad.button4,
            binding.layoutNumPad.button5,
            binding.layoutNumPad.button6,
            binding.layoutNumPad.button7,
            binding.layoutNumPad.button8,
            binding.layoutNumPad.button9,
            binding.layoutNumPad.button0
        )
    }
    private val pins = ArrayDeque<String>()
    private val shakeAnimation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.shake_animation)
    }


    override val layoutResource: Int
        get() = R.layout.activity_login_pin_code

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideStatusAndNavigationBar()
        setupPinSelection()

    }

    private fun setupPinSelection() {
        numPadViews.forEach { numPad ->
            numPad.setOnClickListener {
                if (pins.size < 4) {
                    pins.addLast(numPad.text.toString())
                    addPin()
                }
            }
        }
        binding.layoutNumPad.imageViewClear.setOnClickListener {
            if (pins.isNotEmpty()) {
                pins.removeLast()
                removePin()
            }
        }
    }

    private fun addPin() {
        pinViews[pins.size - 1].setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.ic_pin_circle_solid
            )
        )
        checkPins()
    }

    private fun removePin() {
        pinViews[pins.size].setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.ic_pin_circle
            )
        )
    }

    private fun clearPins() {
        pins.clear()
        pinViews.forEach { pinView ->
            pinView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_pin_circle))
        }
    }

    private fun checkPins() {
        if (pins.size == pinViews.size) {
            if(pins.joinToString("") == "1234"){
                MainActivity.launch(this)
            }else{
                binding.layoutPin.root.startAnimation(shakeAnimation)
                clearPins()
            }
        }
    }
}