package com.rom.garagely.ui.loginActiviy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rom.garagely.R
import com.rom.garagely.databinding.ActivityLoginByMailBinding
import com.rom.garagely.ui.base.BaseActivity
import com.rom.garagely.ui.loginByPin.LoginPinCodeActivity

class LoginByMailActivity : BaseActivity<ActivityLoginByMailBinding>() {
    override val layoutResource: Int
        get() = R.layout.activity_login_by_mail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideStatusAndNavigationBar()
        binding.buttonLogin.setOnClickListener {
            LoginPinCodeActivity.launch(this)
        }
    }
}