package com.rom.garagely.ui.reparingManagement.dashboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.rom.garagely.R
import com.rom.garagely.databinding.FragmentSpaceDashBoardBinding
import com.rom.garagely.model.Client
import com.rom.garagely.ui.Dailog.SearchGuestPopWindow
import com.rom.garagely.ui.client.CreateClientActivity
import com.rom.garagely.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SpaceDashBoardFragment : BaseFragment<FragmentSpaceDashBoardBinding>() {


    private val viewModel: SpaceDashBoardViewModel by viewModels()
    override val layoutResource: Int
        get() = R.layout.fragment_space_dash_board

    private val createClient = registerForActivityResult(CreateClientActivity.Contract()) {

    }

    private var guestPopupWindow: SearchGuestPopWindow? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observableClinet()
        binding.tvNewClient.setOnClickListener {
            createClient.launch("")
        }

        binding.tvNewClient.setOnClickListener {
            viewModel.getClient(true)
        }
    }

    private fun observableClinet() {
        viewModel.clientsLiveData.observe(viewLifecycleOwner) {
            guestPopupWindow = SearchGuestPopWindow(requireContext(), binding.root.width, 400)
            guestPopupWindow?.delegate = object : SearchGuestPopWindow.Delegate {
                override fun onContactSelected(customer: Client) {
                }

                override fun onAddGuest() {

                }
            }
            guestPopupWindow!!.setData(viewModel.clients)
            guestPopupWindow!!.isSearchAble = false
            guestPopupWindow!!.showAsDropDown(
                binding.tvNewClient,
                requireContext().resources.getDimensionPixelOffset(R.dimen.dimen_16),
                -binding.tvNewClient.height - requireContext().resources.getDimensionPixelOffset(R.dimen.dimen_8)
            )
        }
    }
}