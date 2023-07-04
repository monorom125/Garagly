package com.rom.garagely.ui.reparingManagement.dashboard

import BaseViewHolder
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.rom.garagely.databinding.ItemOrdersBinding
import com.rom.garagely.model.Order
import com.rom.garagely.ui.base.BaseRecyclerViewAdapter

class OrderListAdapter : BaseRecyclerViewAdapter<Order, OrderListAdapter.OrderViewHolder>() {




    override fun onCreateItemHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): OrderViewHolder {
        return OrderViewHolder(ItemOrdersBinding.inflate(inflater, parent, false))
    }

    override fun onBindItemHolder(holder: OrderViewHolder, position: Int, context: Context) {
        holder.onBind(items[position])
    }
    inner class OrderViewHolder(binding: ItemOrdersBinding) :
        BaseViewHolder<ItemOrdersBinding>(binding) {

        fun onBind(order: Order) {
            binding.textViewDishName.text = order.product?.name ?: ""
            binding.textViewQuantity.text = order.qty.toString()
            binding.textViewPrice.text = order.product?.price.toString()
            binding.textViewTotal.text = (((order.qty * order.product?.price!!))).toString()
        }
    }
}