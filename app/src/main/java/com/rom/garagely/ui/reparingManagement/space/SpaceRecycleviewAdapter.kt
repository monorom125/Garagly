package com.rom.garagely.ui.reparingManagement.space

import BaseViewHolder
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.rom.garagely.databinding.ItemSpaceBinding
import com.rom.garagely.model.Brand
import com.rom.garagely.model.GarageSpace
import com.rom.garagely.ui.base.BaseRecyclerViewAdapter

class SpaceRecycleViewAdapter : BaseRecyclerViewAdapter<Brand, SpaceRecycleViewAdapter.SpaceViewHolder>() {

    override fun onCreateItemHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): SpaceViewHolder {
        return SpaceViewHolder(ItemSpaceBinding.inflate(inflater, parent, false))
    }

    override fun onBindItemHolder(holder: SpaceViewHolder, position: Int, context: Context) {
        holder.bind(brand = items[position])
    }

    inner class SpaceViewHolder(binding: ItemSpaceBinding) :
        BaseViewHolder<ItemSpaceBinding>(binding) {

        fun bind(brand: Brand) {
            binding.textViewSpace.text = brand.name
        }
    }
}