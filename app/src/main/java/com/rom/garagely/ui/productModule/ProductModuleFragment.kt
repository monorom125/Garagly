package com.rom.garagely.ui.productModule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import com.rom.garagely.R
import com.rom.garagely.common.init
import com.rom.garagely.databinding.FragmentProductModuleBinding
import com.rom.garagely.ui.base.BaseFragment
import com.rom.garagely.ui.productModule.Product.ProductListFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProductModuleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductModuleFragment : BaseFragment<FragmentProductModuleBinding>() {
    override val layoutResource: Int
        get() = R.layout.fragment_product_module

    private val adapter = ProductMenuAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.set(Menu.values().toList())
        binding.rvMenu.init(adapter)
        showProductListFragment()
    }

    private fun showProductListFragment(){
        val fragment = ProductListFragment()
        childFragmentManager.commit {
            add(binding.productContainer.id,fragment,fragment.id.toString())
            addToBackStack(fragment.id.toString())
        }
    }

    enum class Menu {
        Product, Brand, Discount, Tax
    }
}