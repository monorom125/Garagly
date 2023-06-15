package com.rom.garagely.ui.productModule.Product

import AppColor
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rom.garagely.MainActivity
import com.rom.garagely.R
import com.rom.garagely.theme.Typography
import com.rom.garagely.ui.base.BaseComposeFragment

class ProductDetailFragment : BaseComposeFragment() {

    @Composable
    override fun ContentView() {
        ProductView()
    }

    @Composable
    fun ProductView() {
        val nameState = remember {
            mutableStateOf("")
        }
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = AppColor.Background
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        Modifier
                            .size(180.dp, 160.dp)
                            .padding(top = 24.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_placeholder_image),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))

                    OutlinedTextField(
                        value = nameState.value,
                        onValueChange = {
                            nameState.value = it
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .padding(horizontal = 24.dp),
                        label = {
                            Text(
                                "Ënglish Name",
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
                        value = nameState.value,
                        onValueChange = {
                            nameState.value = it
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .padding(
                                horizontal = 24.dp,
                                vertical = 26.dp
                            ),
                        label = {
                            Text(
                                "Price",
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
                }
                Button(
                    onClick = { (activity as MainActivity).pushStack(ProductDetailFragment()) },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = AppColor.Red),
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .padding(end = 16.dp)
                        .wrapContentWidth()
                        .wrapContentHeight()
                        .align(Alignment.BottomEnd)
                ) {
                    Text(
                        text = "Create",
                        style = Typography.h2.copy(color = AppColor.WhiteText),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        }
    }
}