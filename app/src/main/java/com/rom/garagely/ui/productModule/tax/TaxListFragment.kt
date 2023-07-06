package com.rom.garagely.ui.productModule.tax

import AppColor
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
import com.rom.garagely.model.Tax
import com.rom.garagely.theme.Typography
import com.rom.garagely.ui.base.BaseComposeFragment
import com.rom.garagely.ui.productModule.Product.FilterHeaderItem
import com.rom.garagely.ui.productModule.Product.Header
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaxListFragment : BaseComposeFragment() {

    private val viewModel: TaxListViewModel by viewModels()

    @Composable
    override fun ContentView() {
        TaxList()
    }

    private fun goToTaxDetail(tax: Tax?) {
        (requireActivity() as MainActivity).pushStack(TaxDetailFramgent.newInstance(tax))
    }

    @Composable
    fun TaxList() {
        val headers = remember {
            mutableListOf<Header>()
        }
        val textState = remember {
            mutableStateOf("")
        }
        val taxs by viewModel.tax.collectAsState()


        Surface(
            modifier = Modifier.fillMaxSize(),
            color = AppColor.White
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
                            goToTaxDetail(null)
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = AppColor.LightOrange),
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
                        .padding(horizontal = 16.dp)
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
                        .padding(horizontal = 16.dp)
                        .height(1.dp)
                        .background(AppColor.Line)
                )

                LazyColumn(modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp)
                ) {
                    items(taxs.size) {
                        TaxItem(
                            index = it,
                            tax = taxs[it],
                            onItemClick = { vat ->
                                goToTaxDetail(vat)
                            })
                    }
                }
            }
        }
    }

    @Composable
    fun TaxItem(index: Int, tax: Tax, onItemClick: (Tax) -> Unit) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onItemClick.invoke(tax)
                }
                .background(color = AppColor.SearchBackground.takeIf { index % 2 == 0 }
                    ?: AppColor.Background),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .weight(1f),
                text = tax.name ?: "",
                style = Typography.body2,
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = tax.tax_percent.toString(),
                style = Typography.body2,
            )
        }
    }
}