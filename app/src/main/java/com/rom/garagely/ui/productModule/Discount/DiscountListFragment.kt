package com.rom.garagely.ui.productModule.Discount

import AppColor
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.rom.garagely.MainActivity
import com.rom.garagely.R
import com.rom.garagely.model.Discount
import com.rom.garagely.theme.Typography
import com.rom.garagely.ui.base.BaseComposeFragment
import com.rom.garagely.ui.productModule.Product.FilterHeaderItem
import com.rom.garagely.ui.productModule.Product.Header
import com.rom.garagely.util.formatToString
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiscountListFragment : BaseComposeFragment() {

    private val discountDetailFragment by lazy { DiscountDetailFragment() }
    private val viewModel: DiscountListViewModel by viewModels()

    @Composable
    override fun ContentView() {
        DisCountList()
    }

    private fun goToDiscountDetail(discount: Discount) {
        val discountDetailFragment = DiscountDetailFragment.newInstance(discount)
        (requireActivity() as MainActivity).pushStack(discountDetailFragment, withAnimation = true)
    }

    @Composable
    fun DisCountList() {
        val headers = remember {
            mutableListOf<Header>()
        }
        val textState = remember {
            mutableStateOf("")
        }

        val discounts by viewModel.discounts.collectAsState()

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = AppColor.Background
        ) {
            Column(Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BasicTextField(
                        value = textState.value,
                        onValueChange = {
                            textState.value = it
                        },
                        modifier = Modifier
                            .padding(vertical = 16.dp, horizontal = 16.dp)
                            .fillMaxWidth(0.3f),
                        textStyle = Typography.body1,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp) // margin left and right
                                    .fillMaxWidth()
                                    .background(
                                        color = colorResource(id = R.color.white_gray),
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .padding(horizontal = 8.dp, vertical = 8.dp), // inner padding
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_search),
                                        contentDescription = null,
                                        tint = AppColor.HintText
                                    )
                                    if (textState.value.isEmpty()) {
                                        Text(
                                            text = stringResource(id = R.string.search),
                                            style = Typography.body2.copy(color = AppColor.HintText),
                                            modifier = Modifier.padding(start = 10.dp)
                                        )
                                    }
                                    innerTextField()
                                }
                            }
                        }
                    )

                    Button(
                        onClick = {
                            (requireActivity() as MainActivity).pushStack(
                                discountDetailFragment,
                                withAnimation = true
                            )
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = AppColor.Red),
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .padding(end = 16.dp)
                            .wrapContentWidth()
                            .wrapContentHeight()
                    ) {
                        Text(
                            text = "Create",
                            style = Typography.h2.copy(color = AppColor.WhiteText),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .height(IntrinsicSize.Min)
                        .wrapContentWidth()

                ) {
                    viewModel.headers.forEachIndexed { index, header ->
                        this.FilterHeaderItem(header = header)
                    }
                }
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .height(1.dp)
                        .background(AppColor.Line)
                )

                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(discounts.size) {
                        DiscountItem(
                            index = it,
                            discount = discounts[it],
                            onItemClick = { discount ->
                                goToDiscountDetail(discount)
                            })
                    }
                }
            }
        }
    }

    @Composable
    fun DiscountItem(index: Int, discount: Discount, onItemClick: (Discount) -> Unit) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onItemClick.invoke(discount)
                }
                .background(color = AppColor.SearchBackground.takeIf { index % 2 == 0 }
                    ?: AppColor.Background),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(vertical = 8.dp)
                    .weight(1f),
                text = discount.name ?: "",
                style = Typography.body2,
            )
            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .weight(1f),
                text = discount.discount_amount.toString(),
                style = Typography.body2,
            )
            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(vertical = 8.dp)
                    .weight(1f),
                text = discount.start_time.formatToString(),
                style = Typography.body2,
            )
            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(vertical = 8.dp)
                    .weight(1f),
                text = discount.end_time.formatToString(),
                style = Typography.body2,
            )

            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(vertical = 8.dp)
                    .weight(1f),
                text = discount.status.name,
                style = Typography.body2,
                color = colorResource(id = discount.status.getStatusColor())
            )
        }
    }
}