package com.rom.garagely.ui.reparingManagement.space

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.rom.garagely.R
import com.rom.garagely.constant.IntentKey
import com.rom.garagely.constant.IntentKey.CAR
import com.rom.garagely.databinding.FragmentProductDetailDialogBinding
import com.rom.garagely.databinding.FragmentProductModuleBinding
import com.rom.garagely.model.Car

class ProductDetailDialogFragment : DialogFragment() {

    companion object {

        fun newInstance(car: Car): ProductDetailDialogFragment {
            return ProductDetailDialogFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(CAR, car)
                }
            }
        }
    }

    private var car: Car? = null

    private lateinit var binding: FragmentProductDetailDialogBinding

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.DialogThemeCustom)
        car = arguments?.getParcelable(CAR, Car::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            navigationBarColor = ContextCompat.getColor(requireContext(), R.color.white)
            val width = (resources.displayMetrics.widthPixels * 0.8).toInt()
            val height = (resources.displayMetrics.heightPixels * 0.68).toInt()
            if (dialog != null) {
                dialog!!.window!!.apply {
                    setLayout(width, height)
//                    setBackgroundDrawable(
//                        AppCompatResources.getDrawable(
//                            context,
//                            R.drawable.backgroud_radio_button
//                        )
//                    )
                }
                dialog!!.setCanceledOnTouchOutside(true)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpVIew(this.car!!)
    }

    private fun setUpVIew(car: Car) {
        binding.tvTitle.text = car.name
        binding.tvCarDetail.text = car.info
        binding.tvQuantity.text = "Quantity : ${car.quantity}"
        binding.tvBrand.text = "Brand : ${car.brand}"
        binding.tvModel.text = "Model : ${car.model}"

    }
}