package com.rom.garagely.ui.reparingManagement.space

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rom.garagely.R
import com.rom.garagely.common.init
import com.rom.garagely.databinding.FragmentSpaceBinding
import com.rom.garagely.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SpaceFragment : BaseFragment<FragmentSpaceBinding>() {

    override val layoutResource: Int
        get() = R.layout.fragment_space


    private val viewModel: SpaceViewModel by viewModels()
    private val spaceRecyclerViewAdapter = SpaceRecycleViewAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observableSpace()
        setUpView()

    }


    private fun setUpView() {
        binding.spaceRecycleView.init(
            spaceRecyclerViewAdapter,
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        )
    }

    private fun observableSpace() {
        viewModel.space.observe(viewLifecycleOwner) {
            spaceRecyclerViewAdapter.set(it)
        }
    }
}