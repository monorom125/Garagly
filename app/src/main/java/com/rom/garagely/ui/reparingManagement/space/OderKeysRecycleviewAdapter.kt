package com.rom.garagely.ui.reparingManagement.space

import BaseViewHolder
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.rom.garagely.databinding.ItemKeysBinding
import com.rom.garagely.model.Car
import com.rom.garagely.model.Key
import com.rom.garagely.ui.base.BaseRecyclerViewAdapter

class OderKeysRecycleviewAdapter :
    BaseRecyclerViewAdapter<Key, OderKeysRecycleviewAdapter.ItemKeysViewHolder>() {


    override fun onCreateItemHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemKeysViewHolder {
        return ItemKeysViewHolder(ItemKeysBinding.inflate(inflater, parent, false))
    }

    override fun onBindItemHolder(holder: ItemKeysViewHolder, position: Int, context: Context) {
        holder.onBind(items[position])
    }

    inner class ItemKeysViewHolder(binding: ItemKeysBinding) :
        BaseViewHolder<ItemKeysBinding>(binding) {
        fun onBind(key: Key) {
            binding.tvName.text = key.name + "+ $${key.price}"
        }
    }
}