package com.rom.garagely.ui.reparingManagement

import android.os.Bundle
import android.view.View
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.rom.garagely.MainActivity
import com.rom.garagely.R
import com.rom.garagely.databinding.FragmentReparingManagmentBinding
import com.rom.garagely.model.Order
import com.rom.garagely.model.Sell
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
            binding.dashBoardContainer.id,
            binding.spaceContainer.id,
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setTitle("Product Management")
        setUpSpaceFragment()
        setupDashBoardFragment()
    }

    private fun setupDashBoardFragment() {
        val spaceManagerFragment = SpaceDashBoardFragment().apply {

        }
        mainSpaceManagerFragment.addToDashboardFragmentStack(spaceManagerFragment, false)
    }

    private fun setUpSpaceFragment() {
        val spaceFragment = SpaceFragment().apply {
            delegate = object : SpaceFragment.Delegate {
                override fun onCreateMeal(sell: Sell) {
                    (mainSpaceManagerFragment.currentDashboardFragment as SpaceDashBoardFragment).create(
                        sell
                    )
                }

                override fun setOrder(order: Order) {
                    (mainSpaceManagerFragment.currentDashboardFragment as SpaceDashBoardFragment).setOrder(
                        order
                    )
                }

            }
        }
        mainSpaceManagerFragment.addToSpaceFragment(spaceFragment, false)
    }
}

