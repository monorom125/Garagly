package com.rom.garagely.ui.loginByPin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

    override val layoutResource: Int
        get() = R.layout.activity_login_pin_code

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}