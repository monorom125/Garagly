package com.rom.garagely.ui.productModule.tax

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import com.rom.garagely.MainActivity
import com.rom.garagely.R
import com.rom.garagely.common.PreferencesManager
import com.rom.garagely.constant.IntentKey
import com.rom.garagely.constant.SharedPreferenceKeys
import com.rom.garagely.model.Discount
import com.rom.garagely.model.Tax
import com.rom.garagely.theme.Typography
import com.rom.garagely.ui.base.BaseComposeFragment
import com.rom.garagely.ui.productModule.Discount.DiscountDetailFragment
import com.rom.garagely.ui.productModule.category.BrandDetail
import com.rom.garagely.ui.productModule.category.ItemCarSelected
import com.rom.garagely.util.isNull
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TaxDetailFramgent : BaseComposeFragment() {


    companion object {
        fun newInstance(tax: Tax?): TaxDetailFramgent {
            return TaxDetailFramgent().apply {
                arguments = Bundle().apply {
                    putParcelable(IntentKey.TAX, tax)
                }
            }
        }
    }

    private var tax: Tax? = null

    private val viewModel: TaxDetailViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tax = arguments?.getParcelable(IntentKey.TAX, Tax::class.java)
        viewModel.setTax(tax)
    }

    @Composable
    override fun ContentView() {
        TaxDetail()
        if (viewModel.result.collectAsState().value) {
            (requireActivity() as MainActivity).popStack()
        }
    }

    @Composable
    fun TaxDetail() {
        val taxUi by viewModel.tax.collectAsState()

        val nameState = remember {
            mutableStateOf(taxUi?.name ?: "")
        }
        val amount = remember {
            mutableStateOf(taxUi?.tax_percent ?: 0)
        }

        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = AppColor.Background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppColor.Background),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .fillMaxHeight()
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        "Tax Information",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp),
                        style = Typography.body1,
                        color = AppColor.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight(500)
                    )

                    OutlinedTextField(
                        value = nameState.value,
                        onValueChange = {
                            nameState.value = it
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        label = {
                            Text(
                                "English Name",
                                style = Typography.body2.copy(color = AppColor.HintText)
                            )
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedIndicatorColor = AppColor.HintText,
                            unfocusedLabelColor = AppColor.HintText,
                        )
                    )

                    OutlinedTextField(
                        value = amount.value.toString(),
                        onValueChange = {
                            amount.value = it.toInt()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 24.dp),
                        label = {
                            Text(
                                "Percent of tax",
                                style = Typography.body2.copy(color = AppColor.HintText)
                            )
                        },
                        leadingIcon = {
                            Text(
                                "%",
                                style = Typography.body2.copy(color = AppColor.HintText)
                            )
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedIndicatorColor = AppColor.HintText,
                            unfocusedLabelColor = AppColor.HintText,
                        )
                    )

                    Text(
                        text = "Item",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        style = Typography.h2.copy(color = AppColor.Black),
                        textAlign = TextAlign.Left
                    )

                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        color = AppColor.SearchBackground
                    ) {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                        ) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 16.dp)
                            ) {
                                Text(
                                    text = "Total",
                                    style = Typography.body1.copy(AppColor.BodyText)
                                )
                                Text(
                                    text = "0",
                                    style = Typography.body1.copy(AppColor.BodyText),
                                    modifier = Modifier.padding(start = 24.dp)
                                )
                            }
                            for (i in 0..5) {
                                ItemCarSelected(car = null)
                            }
                            Button(
                                onClick = { (activity as MainActivity).pushStack(BrandDetail()) },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(backgroundColor = AppColor.LightOrange),
                                modifier = Modifier
                                    .padding(vertical = 24.dp)
                                    .padding(end = 4.dp)
                                    .wrapContentWidth()
                                    .wrapContentHeight()
                            ) {

                                Text(
                                    text = "Add Product",
                                    style = Typography.h2.copy(color = AppColor.WhiteText),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )
                            }
                        }
                    }

                    Row(
                        Modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween

                    ) {
                        if (!tax.isNull()) {
                            Button(
                                onClick = {

                                },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(backgroundColor = AppColor.Black),
                                modifier = Modifier
                                    .padding(vertical = 24.dp)
                                    .padding(end = 4.dp)
                                    .wrapContentWidth()
                                    .wrapContentHeight()
                            ) {

                                Text(
                                    text = "Delete",
                                    style = Typography.h2.copy(color = AppColor.WhiteText),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(horizontal = 4.dp)
                                )
                            }
                        }

                        Row(
                            Modifier.wrapContentWidth()
                        ) {

                            Button(
                                onClick = { (activity as MainActivity).popStack() },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(backgroundColor = AppColor.White),
                                modifier = Modifier
                                    .padding(vertical = 24.dp)
                                    .padding(end = 4.dp)
                                    .wrapContentWidth()
                                    .wrapContentHeight()
                                    .border(
                                        width = 1.dp,
                                        color = colorResource(id = R.color.colorOrange),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                            ) {
                                Text(
                                    text = "Cancel",
                                    style = Typography.h2.copy(color = AppColor.LightOrange),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(horizontal = 4.dp)
                                )
                            }

                            Button(
                                onClick = {
                                    if (tax.isNull()) {
                                        val tax = Tax().apply {
                                            name = nameState.value
                                            account_id =
                                                PreferencesManager.instance.get(
                                                    SharedPreferenceKeys.USER_ID
                                                )
                                            tax_percent = amount.value
                                        }
                                        viewModel.createProduct(tax)

                                    } else {
                                        this@TaxDetailFramgent.tax!!.apply {
                                            name = nameState.value
                                            account_id =
                                                PreferencesManager.instance.get(
                                                    SharedPreferenceKeys.USER_ID
                                                )
                                            tax_percent = amount.value
                                        }
                                        viewModel.createProduct(this@TaxDetailFramgent.tax!!)
                                    }
                                },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = colorResource(
                                        id = R.color.colorOrange
                                    )
                                ),
                                modifier = Modifier
                                    .padding(vertical = 24.dp)
                                    .padding(end = 4.dp)
                                    .wrapContentWidth()
                                    .wrapContentHeight()
                            ) {
                                Text(
                                    text = "Create".takeIf { tax.isNull() } ?: "Update",
                                    style = Typography.h2.copy(color = AppColor.WhiteText),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(horizontal = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
