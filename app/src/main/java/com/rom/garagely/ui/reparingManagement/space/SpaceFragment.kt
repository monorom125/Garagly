package com.rom.garagely.ui.reparingManagement.space

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.rom.garagely.R
import com.rom.garagely.common.init
import com.rom.garagely.databinding.FragmentSpaceBinding
import com.rom.garagely.model.Car
import com.rom.garagely.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SpaceFragment : BaseFragment<FragmentSpaceBinding>() {

    override val layoutResource: Int
        get() = R.layout.fragment_space

    private val viewModel: SpaceViewModel by viewModels()
    private val spaceRecyclerViewAdapter = SpaceRecycleViewAdapter()
    private val saleRecycleViewAdapter: CarSaleRecycleViewAdapter by lazy {
        CarSaleRecycleViewAdapter().apply {
            setDelegate { item, position ->
                showProductDialog(item)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observableSpace()
        observableCar()
        setUpView()
    }

    fun showProductDialog(car: Car){
        val dialogFragment = ProductDetailDialogFragment.newInstance(car)
        dialogFragment.show(childFragmentManager,null)
    }
    private fun setUpView() {
        binding.spaceRecycleView.init(
            spaceRecyclerViewAdapter,
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        )
        binding.rvProducts.init(
            saleRecycleViewAdapter,
            layoutManager = GridLayoutManager(context, 4, GridLayoutManager.VERTICAL, false)
        )
    }

    private fun observableSpace() {
        viewModel.brand.observe(viewLifecycleOwner) {
            spaceRecyclerViewAdapter.set(it)
        }
    }

    private fun observableCar() {
        lifecycleScope.launch {
            viewModel.car.observe(viewLifecycleOwner) {
                saleRecycleViewAdapter.set(it)
            }

        }
    }

}