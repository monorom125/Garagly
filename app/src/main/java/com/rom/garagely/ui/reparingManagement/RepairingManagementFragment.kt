package com.rom.garagely.ui.reparingManagement

import android.os.Bundle
import android.view.View
import com.rom.garagely.R
import com.rom.garagely.databinding.FragmentReparingManagmentBinding
import com.rom.garagely.ui.base.BaseFragment
import com.rom.garagely.ui.reparingManagement.dashboard.SpaceDashBoardFragment

class RepairingManagementFragment : BaseFragment<FragmentReparingManagmentBinding>() {

    override val layoutResource: Int
        get() = R.layout.fragment_reparing_managment


    val mainSpaceManagerFragment by lazy {
        MainSpaceManagerFragment(
            childFragmentManager,
            binding.dashBoardContainer.id
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDashBoardFragment()
    }

    private fun setupDashBoardFragment() {
        val spaceManagerFragment = SpaceDashBoardFragment()
        mainSpaceManagerFragment.addToDashboardFragmentStack(spaceManagerFragment,false)
    }
}