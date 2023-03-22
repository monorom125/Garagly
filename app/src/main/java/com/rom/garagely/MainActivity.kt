package com.rom.garagely

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.rom.garagely.databinding.ActivityMainBinding
import com.rom.garagely.ui.base.BaseActivity
import com.rom.garagely.ui.base.BaseFragment
import com.rom.garagely.ui.loginByPin.LoginPinCodeActivity
import com.rom.garagely.ui.reparingManagement.RepairingManagementFragment
import java.util.*

class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java).apply {
            })
        }
    }

    private var currentFragment: BaseFragment<*>? = null

    private var selectedDate = Date()

    private var topStackTag: String? = null

    private val backStackFragmentNames = arrayListOf<String>()

    override val layoutResource: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pushStack(RepairingManagementFragment())
        binding.navigationRail.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.repairing -> {
                       pushStack(RepairingManagementFragment())
                           true
                }
                R.id.booking ->{
                        false
                }
                R.id.journal ->{
                    false
                }
                else -> {
                    false
                }
            }
        }
    }

    fun pushStack(fragment: BaseFragment<*>, isReplace: Boolean = false, withAnimation: Boolean = true) {
        currentFragment = fragment
        val tag = fragment.tagID
        topStackTag = tag
        val transaction = supportFragmentManager.beginTransaction()
        if (isReplace) {
            backStackFragmentNames.forEach {
                supportFragmentManager.popBackStack(it, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }
            backStackFragmentNames.clear()
        }
        transaction.apply {
            if (withAnimation) {
                setCustomAnimations(
                    R.anim.enter_right_to_left,
                    R.anim.exit_right_to_left,
                    R.anim.enter_left_to_right,
                    R.anim.exit_left_to_right
                )
            }
            add(binding.layoutContainer.id, fragment, topStackTag)
            addToBackStack(topStackTag)
            commit()
        }
        backStackFragmentNames.add(tag)
    }


}