package com.rom.garagely.ui.reparingManagement.space

import BaseViewHolder
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.rom.garagely.R
import com.rom.garagely.databinding.ItemSaleCarBinding
import com.rom.garagely.model.Car
import com.rom.garagely.ui.base.BaseRecyclerViewAdapter

class CarSaleRecycleViewAdapter :
    BaseRecyclerViewAdapter<Car, CarSaleRecycleViewAdapter.CarSaleViewHolder>() {

    override fun onCreateItemHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): CarSaleViewHolder {
        return CarSaleViewHolder(ItemSaleCarBinding.inflate(inflater, parent, false))
    }

    override fun onBindItemHolder(
        holder: CarSaleViewHolder,
        position: Int,
        context: Context
    ) {
        holder.onBind(items[position])
    }

    inner class CarSaleViewHolder(binding: ItemSaleCarBinding) :
        BaseViewHolder<ItemSaleCarBinding>(binding) {

        init {
            val parentWidth = recyclerView?.width ?: 0
            val itemWidth =
                (parentWidth - (getDimensionPixelOffset(R.dimen.dimen_16) * 3) - (getDimensionPixelOffset(
                    R.dimen.dimen_24dp
                )) * 2) / 4
            binding.imageCar.layoutParams.height = (itemWidth * 0.7).toInt()
            binding.root.clipToOutline = true
        }

        fun onBind(car: Car) {
            Glide.with(binding.root)
                .load(car.image)
                .placeholder(R.drawable.ic_product_holder)
                .fitCenter()
                .into(binding.imageCar)
            binding.tvCarName.text = car.name
            binding.tvCarPrice.text = "$ ${car.price}"

            binding.root.setOnClickListener {
                action?.invoke(car, adapterPosition)
            }
        }
    }
}