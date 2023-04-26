package com.rom.garagely.ui.reparingManagement

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.rom.garagely.R
import com.rom.garagely.ui.reparingManagement.dashboard.SpaceDashBoardFragment

class MainSpaceManagerFragment(
    private val childFragmentManager: FragmentManager,
    private val dashBoardContainId: Int,
    private val spaceContainId : Int,
) {

    val currentDashboardFragment get() = dashboardFragmentStack.last()

    private val dashboardFragmentStack = ArrayDeque<Fragment>()
    private val spaceFragmentStack = ArrayDeque<Fragment>()

    fun clearDashboard() {
        childFragmentManager.commit {
            dashboardFragmentStack.forEach {
                dashboardFragmentStack.removeIf { it !is SpaceDashBoardFragment }
            }
        }
    }

    fun addToDashboardFragmentStack(
        fragment: Fragment,
        withAnimation: Boolean = false,
    ) {
        childFragmentManager.commit {
            setCustomAnimations(R.anim.enter_right_to_left, 0, 0, R.anim.exit_left_to_right)
            add(
                dashBoardContainId,
                fragment,
                fragment.id.toString()
            )
            addToBackStack(fragment.id.toString())
        }
        dashboardFragmentStack.addLast(fragment)
    }

    fun addToSpaceFragment(fragment: Fragment, withAnimation: Boolean = false){
        childFragmentManager.commit {
            setCustomAnimations(R.anim.enter_right_to_left, 0, 0, R.anim.exit_left_to_right)
            add(
                spaceContainId,
                fragment,
                fragment.id.toString()
            )
            addToBackStack(fragment.id.toString())
        }
        spaceFragmentStack.addLast(fragment)
    }

    fun removeFragmentFromDashboard(onComplete: (() -> Unit)? = null) {
        childFragmentManager.popBackStack()
        dashboardFragmentStack.removeLast()
        onComplete?.invoke()
    }
}