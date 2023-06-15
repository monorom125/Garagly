package com.rom.garagely.ui.productModule.Product

import AppColor
import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.rom.garagely.MainActivity
import com.rom.garagely.R
import com.rom.garagely.theme.Typography
import com.rom.garagely.ui.base.BaseComposeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment : BaseComposeFragment() {


    private val viewModel: ProductListViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).setTitle("Product")
    }

    @Composable
    override fun ContentView() {

        MainView(viewModel)
    }

    @Composable
    fun MainView(viewModel: ProductListViewModel) {
        val headers = remember {
            viewModel.headers
        }
        val textState = remember {
            mutableStateOf("")
        }
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFFE5E5E5)
        ) {
            Column(Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BasicTextField(
                        value = textState.value,
                        onValueChange = { it ->
                            textState.value = it
                        },
                        modifier = Modifier
                            .padding(vertical = 16.dp)
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
                        onClick = { (activity as MainActivity).pushStack(ProductDetailFragment()) },
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
                    headers.forEachIndexed { index, header ->
                        this.FilterHeaderItem(header = header)
                    }
                }
            }
        }
    }
}

    @Composable
    fun RowScope.FilterHeaderItem(header: Header) {
        Row(
            modifier = Modifier
                .weight(header.weight)
                .fillMaxHeight()
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.wrapContentSize(),
                text = stringResource(id = header.name),
                style = Typography.h3,
            )
            Image(
                modifier = Modifier.padding(start = 4.dp),
                painter = painterResource(id = header.orderBy.iconResource),
                contentDescription = null
            )
        }
    }
