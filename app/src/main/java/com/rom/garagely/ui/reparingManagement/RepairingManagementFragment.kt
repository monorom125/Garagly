package com.rom.garagely.ui.reparingManagement

import android.os.Bundle
import android.view.View
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.rom.garagely.R
import com.rom.garagely.databinding.FragmentReparingManagmentBinding
import com.rom.garagely.ui.base.BaseFragment
import com.rom.garagely.ui.reparingManagement.dashboard.SpaceDashBoardFragment
import com.rom.garagely.ui.reparingManagement.space.SpaceFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepairingManagementFragment : BaseFragment<FragmentReparingManagmentBinding>() {

    override val layoutResource: Int
        get() = R.layout.fragment_reparing_managment


    private val mainSpaceManagerFragment by lazy {
        MainSpaceManagerFragment(
            childFragmentManager,
            binding.dashBoardContainer.id
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDashBoardFragment()
        setUpSpaceFragment()
    }

    private fun setupDashBoardFragment() {
        val spaceManagerFragment = SpaceDashBoardFragment()
        mainSpaceManagerFragment.addToDashboardFragmentStack(spaceManagerFragment, false)
    }

    private fun setUpSpaceFragment() {
        val spaceFragment = SpaceFragment()
        childFragmentManager.commit {
            add(
                binding.spaceContainer.id,
                spaceFragment,
                spaceFragment.id.toString()
            )
        }
    }
}

