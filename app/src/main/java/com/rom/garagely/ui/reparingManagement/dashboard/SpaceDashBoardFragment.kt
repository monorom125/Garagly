package com.rom.garagely.ui.reparingManagement.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import com.rom.garagely.R
import com.rom.garagely.databinding.FragmentSpaceDashBoardBinding
import com.rom.garagely.ui.base.BaseFragment

class SpaceDashBoardFragment : BaseFragment<FragmentSpaceDashBoardBinding>(){

    override val layoutResource: Int
        get() = R.layout.fragment_space_dash_board

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.radioView.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                binding.operatingCheck.id -> {}
                binding.bookingCheck.id -> {}
            }
        }
    }
}