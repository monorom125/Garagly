package com.rom.garagely.ui.productModule.Discount

import AppColor
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.rom.garagely.MainActivity
import com.rom.garagely.R
import com.rom.garagely.common.PreferencesManager
import com.rom.garagely.constant.IntentKey
import com.rom.garagely.constant.SharedPreferenceKeys
import com.rom.garagely.model.Discount
import com.rom.garagely.theme.Typography
import com.rom.garagely.ui.base.BaseComposeFragment
import com.rom.garagely.ui.productModule.category.BrandDetail
import com.rom.garagely.ui.productModule.category.ItemCarSelected
import com.rom.garagely.util.formatToString
import com.rom.garagely.util.toDate
import dagger.hilt.android.AndroidEntryPoint
import java.time.*

@AndroidEntryPoint
class DiscountDetailFragment : BaseComposeFragment() {


    companion object {
        fun newInstance(discount: Discount): DiscountDetailFragment {
            return DiscountDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(IntentKey.DISCOUNT, discount)
                }
            }
        }
    }


    private var discount: Discount? = null
    private val viewModel: DiscountDetailViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
          discount = arguments?.getParcelable(IntentKey.DISCOUNT, Discount::class.java)
        viewModel.setDiscount(discount)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    override fun ContentView() {
        val onSuccess = remember {
            viewModel.onSuccess
        }
        DiscountDetailView(viewModel)
        if(onSuccess.value){
            (requireActivity() as MainActivity).popStack()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DiscountDetailView(viewModel: DiscountDetailViewModel) {
        var isEndDate = false
        val discountUi by viewModel.discount.collectAsState()

        val interactionSource = remember {
            MutableInteractionSource()
        }
        val nameState = remember {
            mutableStateOf(discountUi?.name ?: "")
        }

        val isAmount = remember {
            mutableStateOf(false)
        }

        val statDate = remember {
            mutableStateOf(discountUi?.start_time)
        }

        val endDate = remember {
            mutableStateOf(discountUi?.end_time)
        }

        val promotionAmountOrPercent = remember {
            mutableStateOf("Percent of promotion")
        }

        val prefix = remember {
            mutableStateOf("%")
        }

        if (isAmount.value) {
            prefix.value = "$"
            promotionAmountOrPercent.value = "Percent of Amount"
        } else {
            prefix.value = "%"
            promotionAmountOrPercent.value = "Percent of promotion"
        }

        val amount = remember {
            mutableStateOf(discountUi?.discount_amount ?: 0.00)
        }

        val calendarState = rememberSheetState()

        CalendarDialog(state = calendarState,
            selection = CalendarSelection.Date {
                if (isEndDate) {
                    endDate.value = it.toDate()
                } else {

                    statDate.value = it.toDate()
                }
            })

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
                        "Promotion Information",
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

                    Text(
                        text = "Type of promotion",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 24.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Percent",
                                style = Typography.h2
                            )
                            Checkbox(
                                modifier = Modifier.wrapContentSize(),
                                checked = !isAmount.value,
                                onCheckedChange = {
                                    isAmount.value = !isAmount.value
                                },
                                colors = CheckboxDefaults.colors(AppColor.Red)
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(start = 24.dp)
                        ) {
                            Text(
                                text = "Amount",
                                style = Typography.h2
                            )
                            Checkbox(
                                modifier = Modifier.wrapContentSize(),
                                checked = isAmount.value,
                                onCheckedChange = {
                                    isAmount.value = !isAmount.value
                                },
                                colors = CheckboxDefaults.colors(AppColor.Red)
                            )
                        }
                    }

                    OutlinedTextField(
                        value = amount.value.toString(),
                        onValueChange = {
                            amount.value = it.toDouble()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                            .padding(top = 24.dp),
                        label = {
                            Text(
                                promotionAmountOrPercent.value,
                                style = Typography.body2.copy(color = AppColor.HintText)
                            )
                        },
                        leadingIcon = {
                            Text(
                                prefix.value,
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
                        text = "Date and Time",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp),
                        style = Typography.h2.copy(color = AppColor.Black)
                    )

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp)
                    ) {

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(
                                    start = 24.dp, end = 12.dp
                                )

                        ) {
                            OutlinedTextField(
                                value = statDate.value?.formatToString()?:"",
                                onValueChange = {
//                                    nameState.value = it
                                },
                                readOnly = true,
                                modifier = Modifier
                                    .clickable {
                                        calendarState.show()
                                    },
                                label = {
                                    Text(
                                        "Start Date ",
                                        style = Typography.body2.copy(color = AppColor.HintText)
                                    )
                                },
                                shape = RoundedCornerShape(10.dp),
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = Color.Transparent,
                                    focusedIndicatorColor = AppColor.HintText,
                                    unfocusedLabelColor = AppColor.HintText,
                                ),
                            )
                            Box(modifier = Modifier
                                .matchParentSize()
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = null
                                ) {
                                    isEndDate = false
                                    calendarState.show()
                                })
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 12.dp, end = 24.dp)
                        ) {
                            OutlinedTextField(
                                value = endDate.value?.formatToString()?:"",
                                onValueChange = {
                                    nameState.value = it
                                },
                                modifier = Modifier,
                                label = {
                                    Text(
                                        "End Date",
                                        style = Typography.body2.copy(color = AppColor.HintText)
                                    )
                                },
                                readOnly = true,
                                shape = RoundedCornerShape(10.dp),
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = Color.Transparent,
                                    focusedIndicatorColor = AppColor.HintText,
                                    unfocusedLabelColor = AppColor.HintText,
                                )
                            )

                            Box(
                                modifier = Modifier
                                    .matchParentSize()
                                    .clickable(
                                        interactionSource = interactionSource,
                                        indication = null
                                    ) {
                                        isEndDate = true
                                        calendarState.show()
                                    }
                            )
                        }
                    }

                    Text(
                        text = "Da",
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .padding(top = 24.dp, bottom = 16.dp),
                        style = Typography.h2.copy(color = AppColor.Black),
                        textAlign = TextAlign.Left
                    )

                    Surface(
                        modifier = Modifier.fillMaxWidth(0.6f),
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
                }
                Row(
                    Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween

                ) {
                    if (viewModel.discount != null) {
                        Button(
                            onClick = { },
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
                                if (discount == null) {
                                    val discount = Discount().apply {
                                        name = nameState.value
                                        account_id =
                                            PreferencesManager.instance.get(SharedPreferenceKeys.USER_ID)
                                        discount_amount = amount.value
                                        start_time = statDate.value
                                        end_time = endDate.value
                                        active = true
                                        type = Discount.Type.Percent.takeIf { !isAmount.value }
                                            ?: Discount.Type.Amount
                                    }
                                    viewModel.upsertDiscount(discount)

                                } else {
                                    this@DiscountDetailFragment.discount!!.apply {
                                        name = nameState.value
                                        account_id =
                                            PreferencesManager.instance.get(SharedPreferenceKeys.USER_ID)
                                        discount_amount = amount.value
                                        start_time = statDate.value
                                        end_time = endDate.value
                                        active = getTheActive()
                                        type = Discount.Type.Percent.takeIf { !isAmount.value }
                                            ?: Discount.Type.Amount
                                    }
                                    viewModel.upsertDiscount(discount!!)
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
                                text = "Create".takeIf { discount == null } ?: "Update",
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

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Calender() {

    }
}
