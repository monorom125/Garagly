package com.rom.garagely.ui.productModule

import BaseViewHolder
import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import com.rom.garagely.R
import com.rom.garagely.databinding.ItemProductMenuBinding
import com.rom.garagely.ui.base.BaseRecyclerViewAdapter

class ProductMenuAdapter :
    BaseRecyclerViewAdapter<ProductModuleFragment.Menu, ProductMenuAdapter.MenuItemViewHolder>() {


    var selected: ProductModuleFragment.Menu = ProductModuleFragment.Menu.Product
    override fun onCreateItemHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): MenuItemViewHolder {
        return MenuItemViewHolder(ItemProductMenuBinding.inflate(inflater, parent, false))
    }

    override fun onBindItemHolder(holder: MenuItemViewHolder, position: Int, context: Context) {
        holder.onBind(menu = items[position])
    }

    inner class MenuItemViewHolder(binding: ItemProductMenuBinding) :
        BaseViewHolder<ItemProductMenuBinding>(binding = binding) {
        fun onBind(menu: ProductModuleFragment.Menu) {

            if (selected.name == menu.name) {
                binding.textViewMenuName.setBackgroundResource(R.drawable.bg_orang_round_8)
                binding.textViewMenuName.setTextColor(getColor(R.color.white))
            } else {
                binding.textViewMenuName.setBackgroundResource(R.drawable.backgrund_white_round_8dp)
                binding.textViewMenuName.setTextColor(getColor(R.color.black))
            }
            binding.textViewMenuName.text = menu.name
            binding.root.setOnClickListener {
                action?.invoke(menu, adapterPosition)
                selected = menu
                notifyDataSetChanged()
            }
        }
    }
}