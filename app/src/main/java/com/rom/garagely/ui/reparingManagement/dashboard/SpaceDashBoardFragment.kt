package com.rom.garagely.ui.reparingManagement.dashboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.rom.garagely.R
import com.rom.garagely.common.init
import com.rom.garagely.databinding.FragmentSpaceDashBoardBinding
import com.rom.garagely.model.Client
import com.rom.garagely.model.Order
import com.rom.garagely.model.Sell
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

    fun create(sell: Sell) {
        viewModel.createSell(sell)
    }

    fun setOrder(order: Order) {
        viewModel.upsertOrder(order) {
            orderListAdapter.add(it)
        }
    }

    private var guestPopupWindow: SearchGuestPopWindow? = null
    private val orderListAdapter by lazy {
        OrderListAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observableOrders()
        observableClinet()
        binding.rvOrderList.init(orderListAdapter)
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

    private fun observableOrders() {
        viewModel.orders.observe(viewLifecycleOwner) {
            orderListAdapter.set(it)
        }
    }

    private fun observableSell() {
        viewModel.clientsLiveData.observe(viewLifecycleOwner) {

        }
    }

    interface Delegate {
        fun onGetSell(sell: Sell?)
    }
}