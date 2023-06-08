package com.rom.garagely.ui.productModule

import BaseViewHolder
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.rom.garagely.databinding.ItemProductMenuBinding
import com.rom.garagely.ui.base.BaseRecyclerViewAdapter

class ProductMenuAdapter : BaseRecyclerViewAdapter<ProductModuleFragment.Menu, ProductMenuAdapter.MenuItemViewHolder> (){


    override fun onCreateItemHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): MenuItemViewHolder {
        return MenuItemViewHolder(ItemProductMenuBinding.inflate(inflater,parent,false))
    }

    override fun onBindItemHolder(holder: MenuItemViewHolder, position: Int, context: Context) {
        holder.onBind(menu = items[position])
    }
    inner class MenuItemViewHolder(binding: ItemProductMenuBinding) : BaseViewHolder<ItemProductMenuBinding>(binding = binding ){
            fun onBind(menu : ProductModuleFragment.Menu){
                binding.textViewMenuName.text = menu.name
            }
    }
}