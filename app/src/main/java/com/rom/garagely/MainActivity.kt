package com.rom.garagely

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.rom.garagely.custom.view.GaraglyHeaderBar
import com.rom.garagely.databinding.ActivityMainBinding
import com.rom.garagely.model.User
import com.rom.garagely.ui.base.BaseActivity
import com.rom.garagely.ui.loginByPin.LoginPinCodeActivity
import com.rom.garagely.ui.productModule.ProductModuleFragment
import com.rom.garagely.ui.reparingManagement.RepairingManagementFragment
import com.rom.garagely.util.parcelable
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object {
        fun launch(context: Context, user: User) {
            context.startActivity(Intent(context, MainActivity::class.java).apply {
                putExtra("USER", user)
            })
        }
    }

    var delegate: Delegate? = null
    private var currentFragment: Fragment? = null
    private var selectedDate = Date()
    private var user: User? = null
    private var topStackTag: String? = null
    private val backStackFragmentNames = arrayListOf<String>()

    override val layoutResource: Int
        get() = R.layout.activity_main

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.user = intent.parcelable("USER", User::class.java)!!
        binding.posHeaderToolbar.setGaragelyName(this.user?.name ?: "")
        setTitle("Retail Management")
        onHeaderListener()
        pushStack(RepairingManagementFragment())
        binding.navigationRail.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.repairing -> {
                    pushStack(RepairingManagementFragment(), true)
                    true
                }

                R.id.booking -> {
                    setTitle("Product Module")
                    pushStack(ProductModuleFragment(), true)
                    true
                }

                R.id.journal -> {
                    false
                }

                else -> {
                    false
                }
            }
        }

    }

    fun setTitle(title: String) {
        binding.posHeaderToolbar.setTitle(title)

    }
    fun isVisible(isVisable : Boolean = false){
        binding.posHeaderToolbar.isVisibleBack(isVisable)
    }

    fun onHeaderListener() {

        binding.posHeaderToolbar.delegate = object : GaraglyHeaderBar.Delegate {
            override fun onLogoutClick() {
                LoginPinCodeActivity.launch(this@MainActivity)
            }

            override fun onBackClick() {
                popStack()
            }
        }
    }


    fun pushStack(
        fragment: Fragment,
        isReplace: Boolean = false,
        withAnimation: Boolean = true
    ) {
        currentFragment = fragment
        val tag = fragment.tag ?: ""
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

    fun popStack() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            topStackTag =
                supportFragmentManager.getBackStackEntryAt(supportFragmentManager.backStackEntryCount - 2).name
            supportFragmentManager.popBackStack()
            updateFragment()
        } else {
            finish()
        }
    }

    private fun updateFragment() {
        currentFragment = supportFragmentManager.fragments.firstOrNull { it.tag == topStackTag }
        delegate?.popBack()

    }


    interface Delegate {

        fun popBack() {}
    }
}