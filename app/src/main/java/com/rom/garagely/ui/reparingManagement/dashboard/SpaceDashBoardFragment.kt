package com.rom.garagely.ui.reparingManagement.dashboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.rom.garagely.MainActivity
import com.rom.garagely.R
import com.rom.garagely.common.PreferencesManager
import com.rom.garagely.common.init
import com.rom.garagely.constant.SharedPreferenceKeys
import com.rom.garagely.databinding.FragmentSpaceDashBoardBinding
import com.rom.garagely.model.Client
import com.rom.garagely.model.Order
import com.rom.garagely.model.Sell
import com.rom.garagely.model.Status
import com.rom.garagely.ui.Dailog.SearchGuestPopWindow
import com.rom.garagely.ui.client.CreateClientActivity
import com.rom.garagely.ui.base.BaseFragment
import com.rom.garagely.ui.payment.PaymentPageFragment
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

    private var guestPopupWindow: SearchGuestPopWindow? = null
    private val orderListAdapter by lazy {
        OrderListAdapter()
    }

    fun create(sell: Sell) {
        viewModel.createSell(sell)
    }

    fun setOrder(order: Order) {
        if (viewModel.sell.isNull()) {
            val sell =
                Sell(account_id = PreferencesManager.instance.get(SharedPreferenceKeys.USER_ID))
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        observableOrders()
        observableClinet()
        observableSell()
    }

    private fun updateClearButton(isOrderDishEmpty: Boolean) {
        val clearIcon = R.drawable.ic_clear.takeIf { !isOrderDishEmpty }
            ?: R.drawable.ic_delete_24dp
        val clearText = getString(R.string.CLEAR).takeIf { !isOrderDishEmpty }
            ?: getString(R.string.delete)
        val paddingTop = requireContext().resources.getDimensionPixelOffset(R.dimen.dimen_12)
            .takeIf { !isOrderDishEmpty }
            ?: requireContext().resources.getDimensionPixelOffset(R.dimen.dimen_6)
        binding.buttonClear.apply {
            text = clearText
            setCompoundDrawablesWithIntrinsicBounds(0, clearIcon, 0, 0)
            val paddingHorizontal =
                requireContext().resources.getDimensionPixelOffset(R.dimen.dimen_16)
            setPadding(paddingHorizontal, paddingTop, paddingHorizontal, 0)
        }
    }


    fun setUpView(){
        binding.rvOrderList.init(orderListAdapter)
        binding.tvNewClient.setOnClickListener {
            viewModel.getClient(true)
        }
        orderListAdapter.setDelegate { item, _ ->
            viewModel.deletedOrder(item)
        }

        binding.buttonClear.setOnClickListener {
            if (viewModel.sell.isNull()) return@setOnClickListener
            if (orderListAdapter.items.isEmpty()) {
                viewModel.closeSell(viewModel.sell!!, true)
            }
            if (orderListAdapter.items.all { it.status == Status.Paid }) {
                viewModel.sell?.status = Sell.Status.Done
                viewModel.closeSell(viewModel.sell!!)
            }
            if(orderListAdapter.items.any { it.status == Status.Order }){
                val unPaidOrders = orderListAdapter.items.filter { it.status == Status.Order }
                viewModel.clearProduct(unPaidOrders)
            }
        }

        binding.buttonPay.setOnClickListener {
            val paymentPayPage = PaymentPageFragment()
            (requireActivity() as MainActivity).isVisible(true)
            (requireActivity() as MainActivity).pushStack(paymentPayPage, withAnimation = true)
        }
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
            updateClearButton(orderListAdapter.items.isEmpty())
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