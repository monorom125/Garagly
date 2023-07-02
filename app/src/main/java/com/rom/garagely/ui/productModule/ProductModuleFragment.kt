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
import com.rom.garagely.ui.productModule.Discount.DiscountListFragment
import com.rom.garagely.ui.productModule.Product.ProductListFragment
import com.rom.garagely.ui.productModule.category.BrandFragment
import com.rom.garagely.ui.productModule.tax.TaxListFragment

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

    private val productListFragment by lazy {
        ProductListFragment()
    }
    private val brandFragment by lazy {
        BrandFragment()
    }
    private val discountListFragment by lazy {
        DiscountListFragment()
    }
    private val taxListFragment by lazy {
        TaxListFragment()
    }

    private val adapter = ProductMenuAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.set(Menu.values().toList())
        binding.rvMenu.init(adapter)

        childFragmentManager.commit {
            add(binding.productContainer.id, productListFragment, productListFragment.id.toString())
        }

        adapter.setDelegate { item, _ ->
            when (item) {
                Menu.Product -> {
                    replaceFragment(productListFragment)
                }

                Menu.Brand -> {
                    replaceFragment(brandFragment)

                }

                Menu.Tax -> {
                    replaceFragment(taxListFragment)
                }

                Menu.Discount -> {
                    replaceFragment(discountListFragment)
                }
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        childFragmentManager.commit {
            replace(binding.productContainer.id, fragment, fragment.tag.toString())
        }
    }

    enum class Menu {
        Product, Brand, Discount, Tax
    }
}