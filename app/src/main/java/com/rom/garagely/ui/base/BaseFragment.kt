package com.rom.garagely.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<T: ViewDataBinding>(): Fragment() {

    abstract val layoutResource: Int
    protected open val progressBar: ProgressBar? = null
    protected lateinit var binding: T
    var tagID: String = this::class.java.simpleName


    var popBackStackDelegate: ((tag: String?) -> Unit)? = null

    private var isFirstVisible = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, layoutResource, container, false)
        return binding.root
    }
}