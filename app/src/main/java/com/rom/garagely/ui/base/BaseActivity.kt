package com.rom.garagely.ui.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {

    abstract val layoutResource: Int
    protected lateinit var binding: T


    open val progressBar: ProgressBar? = null

    val screenWidth: Int
        get() {
            val displayMetrics = applicationContext.resources.displayMetrics
            return displayMetrics.widthPixels
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutResource)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE

    }
}