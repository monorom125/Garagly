package com.rom.garagely

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rom.garagely.databinding.ActivityMainBinding
import com.rom.garagely.ui.base.BaseActivity
import com.rom.garagely.ui.loginByPin.LoginPinCodeActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java).apply {
            })
        }
    }

    override val layoutResource: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}