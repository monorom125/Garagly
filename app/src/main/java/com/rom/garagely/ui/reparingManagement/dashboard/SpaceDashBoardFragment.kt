package com.rom.garagely.ui.reparingManagement.dashboard

import android.os.Bundle
import android.view.View
import com.rom.garagely.R
import com.rom.garagely.databinding.FragmentSpaceDashBoardBinding
import com.rom.garagely.ui.client.CreateClientActivity
import com.rom.garagely.ui.base.BaseFragment

class SpaceDashBoardFragment : BaseFragment<FragmentSpaceDashBoardBinding>(){

    override val layoutResource: Int
        get() = R.layout.fragment_space_dash_board

    private val createClient = registerForActivityResult(CreateClientActivity.Contract()){

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvNewClient.setOnClickListener {
            createClient.launch("")
        }

        binding.tvNewClient.setOnClickListener {


        }
    }
}