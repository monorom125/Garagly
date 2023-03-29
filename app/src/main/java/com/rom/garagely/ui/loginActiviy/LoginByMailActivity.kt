package com.rom.garagely.ui.loginActiviy

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.coroutineScope
import com.rom.garagely.R
import com.rom.garagely.databinding.ActivityLoginByMailBinding
import com.rom.garagely.ui.base.BaseActivity
import com.rom.garagely.ui.loginByPin.LoginPinCodeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginByMailActivity : BaseActivity<ActivityLoginByMailBinding>() {

    companion object{
        fun launch(context : Context){
            context.startActivity(Intent(context,LoginByMailActivity::class.java ))
        }
    }
    override val layoutResource: Int
        get() = R.layout.activity_login_by_mail
    private val viewModel : LoginByMailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideStatusAndNavigationBar()
        binding.buttonLogin.setOnClickListener {
            viewModel.loginByMail(email = binding.emailView.text.toString().trim(),binding.passwordView.text.toString().trim())
        }
        lifecycle.coroutineScope.launch {
            viewModel.result.collect(){
                if(it){
                    LoginPinCodeActivity.launch(this@LoginByMailActivity)
                    finish()
                }
                else{
                    binding.emailView.setText("")
                    binding.passwordView.setText("")
                }
            }
        }
    }
}