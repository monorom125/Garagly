package com.rom.garagely.ui.productModule.Product

import AppColor
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.rom.garagely.MainActivity
import com.rom.garagely.R
import com.rom.garagely.model.Car
import com.rom.garagely.theme.Typography
import com.rom.garagely.ui.base.BaseComposeFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID

@AndroidEntryPoint
class ProductDetailFragment : BaseComposeFragment() {

    private val viewModel: ProductDetailViewModel by viewModels()

    @Composable
    override fun ContentView() {
        ProductView()
        val result = viewModel.result.collectAsState()
        if (result.value) {
            (activity as MainActivity).popStack()
        }
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
                        .padding(bottom = 60.dp)
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

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.7f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = nameState.value,
                            onValueChange = {
                                nameState.value = it
                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(
                                    horizontal = 24.dp,
                                )
                                .padding(end = 16.dp),
                            label = {
                                Text(
                                    "Brand",
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
                                .weight(1f)
                                .padding(
                                    end = 24.dp,
                                ),
                            label = {
                                Text(
                                    "Model",
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
                                "Detail Car",
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

                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .padding(top = 24.dp)
                    ) {
                        Row {
                            Row(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Sell",
                                    style = Typography.h2
                                )
                                Checkbox(
                                    modifier = Modifier.wrapContentSize(),
                                    checked = true,
                                    onCheckedChange = {
                                    },
                                    colors = CheckboxDefaults.colors(AppColor.Red)
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Rent",
                                    style = Typography.h2
                                )
                                Checkbox(
                                    modifier = Modifier.wrapContentSize(),
                                    checked = true,
                                    onCheckedChange = {
                                    },
                                    colors = CheckboxDefaults.colors(AppColor.Red)
                                )
                            }
                        }

                        Text(
                            text = "Quantity",
                            style = Typography.h2
                        )
                        Row(
                            modifier = Modifier.padding(24.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_minus),
                                contentDescription = null
                            )
                            Text(
                                modifier = Modifier.padding(16.dp),
                                text = "0",
                                style = Typography.h2
                            )
                            Image(
                                painter = painterResource(id = R.drawable.plus_fill),
                                contentDescription = null
                            )
                        }
                    }

                    Text(
                        "Key",
                        style = Typography.h2.copy(
                            color = AppColor.Black
                        ),
                        modifier = Modifier
                            .padding(start = 24.dp)
                            .fillMaxWidth(0.7f)
                    )
                    val keys = remember {
                        mutableStateOf<ArrayList<Int>>(arrayListOf(1))
                    }
                    keys.value.forEach {
                        ItemKey(i = it)
                    }
                }

                Row(
                    modifier = Modifier.align(Alignment.BottomEnd)
                ) {
                    Button(
                        onClick = {
                            (activity as MainActivity).popStack()
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = AppColor.DarkLightWhite),
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .padding(end = 16.dp)
                            .wrapContentWidth()
                            .wrapContentHeight()
                    ) {
                        Text(
                            text = "Back",
                            style = Typography.h2.copy(color = AppColor.WhiteText),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }

                    Button(
                        onClick = {
                            val car = Car().apply {
                                id = UUID.randomUUID().toString()
                                name = nameState.value
                                price = 100.0
                            }
                            viewModel.createProduct(car)
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
            }
        }
    }

    @Composable
    fun ItemKey(i: Int) {

        Row(
            modifier = Modifier
                .fillMaxWidth(0.7f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val keyName = remember {
                mutableStateOf("")
            }
            val priceName = remember {
                mutableStateOf("")
            }
            Image(
                painter = painterResource(id = R.drawable.plus_fill), contentDescription = null
            )
            OutlinedTextField(
                value = keyName.value,
                onValueChange = {
                    keyName.value = it
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        horizontal = 24.dp,
                    )
                    .padding(end = 16.dp),
                label = {
                    Text(
                        "key Name",
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
                value = priceName.value,
                onValueChange = {
                    priceName.value = it
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        end = 24.dp,
                    ),
                label = {
                    Text(
                        "Key Price",
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
    }
}