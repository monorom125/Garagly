package com.rom.garagely.ui.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import com.rom.garagely.R
import com.rom.garagely.databinding.FragmentPaymentPageBinding
import com.rom.garagely.ui.base.BaseFragment



class PaymentPageFragment : BaseFragment<FragmentPaymentPageBinding>(){

    override val layoutResource: Int
        get() = R.layout.fragment_payment_page

    private val cashFragment by lazy { CashFragment() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childFragmentManager.commit {
            add(binding.detailsPageHolder.id, cashFragment)
        }
    }
}