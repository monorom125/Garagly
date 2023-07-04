package com.rom.garagely.ui.reparingManagement.dashboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.rom.garagely.R
import com.rom.garagely.common.PreferencesManager
import com.rom.garagely.common.init
import com.rom.garagely.constant.SharedPreferenceKeys
import com.rom.garagely.databinding.FragmentSpaceDashBoardBinding
import com.rom.garagely.model.Client
import com.rom.garagely.model.Order
import com.rom.garagely.model.Sell
import com.rom.garagely.ui.Dailog.SearchGuestPopWindow
import com.rom.garagely.ui.client.CreateClientActivity
import com.rom.garagely.ui.base.BaseFragment
import com.rom.garagely.util.formatToHour
import com.rom.garagely.util.isNull
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking

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
        if (viewModel.sell.isNull()) {
            val sell = Sell(account_id = PreferencesManager.instance.get(SharedPreferenceKeys.USER_ID))
            order.sell_id = sell.id

            viewModel.createSell(sell) {
                viewModel.upsertOrder(order)
            }
        } else {
            val isSameOrder = orderListAdapter.items.firstOrNull { it.isTheSame(order) }
            if (!isSameOrder.isNull()) {
                order.qty = isSameOrder!!.qty + 1
                order.id = isSameOrder.id
                order.sell_id = isSameOrder.sell_id
            } else {
                order.sell_id = viewModel.sell!!.id
            }
            viewModel.upsertOrder(order)
        }
    }

    private var guestPopupWindow: SearchGuestPopWindow? = null
    private val orderListAdapter by lazy {
        OrderListAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvOrderList.init(orderListAdapter)
        binding.tvNewClient.setOnClickListener {
            viewModel.getClient(true)
        }
        orderListAdapter.setDelegate { item, _ ->
         viewModel.deletedOrder(item)
        }
        observableOrders()
        observableClinet()
        observableSell()
    }

    private fun observableClinet() {
        viewModel.clientsLiveData.observe(viewLifecycleOwner) {
            guestPopupWindow = SearchGuestPopWindow(requireContext(), binding.root.width, 400)
            guestPopupWindow?.delegate = object : SearchGuestPopWindow.Delegate {
                override fun onContactSelected(customer: Client) {
                    if (viewModel.sell.isNull()) {
                        return
                    }
                    viewModel.sell?.client = customer
                    viewModel.createSell(viewModel.sell!!)
                }

                override fun onAddGuest() {
                    createClient.launch("")
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
        viewModel.sellLiveData.observe(viewLifecycleOwner) {
            if (it.isNull()) {
                binding.tvSell.text = "no order"
            }
            binding.tvSell.text = it?.date.formatToHour()

        }
    }

    interface Delegate {
        fun onGetSell(sell: Sell?)
        fun onCloseMeal()
    }
}