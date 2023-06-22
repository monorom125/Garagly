package com.rom.garagely.ui.productModule.category

import AppColor
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.rom.garagely.MainActivity
import com.rom.garagely.R
import com.rom.garagely.composeView.CoilImageLoader
import com.rom.garagely.model.Brand
import com.rom.garagely.theme.Typography
import com.rom.garagely.ui.base.BaseComposeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BrandFragment : BaseComposeFragment() {

    private val viewModel : BrandListViewModel by viewModels()

    private val mainActivity : MainActivity?
        get() = activity as? MainActivity

    @Composable
    override fun ContentView() {
        BrandView()
        InitFragment()
    }


    @Composable
    fun BrandView() {

        val brands by viewModel.brands.collectAsState()

        val searchState = remember {
            mutableStateOf("")
        }
        Surface(
            color = AppColor.SearchBackground,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(Modifier.fillMaxWidth()) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Button(
                        onClick = { },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = AppColor.White),
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .padding(end = 8.dp)
                            .wrapContentWidth()
                            .wrapContentHeight()
                    ) {

                        Text(
                            text = "Name",
                            style = Typography.h2.copy(color = AppColor.BodyText),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )

                        Image(
                            painter = painterResource(id = R.drawable.ic_updown_ui),
                            contentDescription = null
                        )
                    }

                    Row(
                        Modifier.wrapContentWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        BasicTextField(
                            value = searchState.value,
                            onValueChange = { it ->
                                searchState.value = it
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
                                            color = colorResource(id = R.color.white),
                                            shape = RoundedCornerShape(10.dp)
                                        )
                                        .padding(
                                            horizontal = 8.dp,
                                            vertical = 8.dp
                                        ), // inner padding
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_search),
                                            contentDescription = null,
                                            tint = AppColor.HintText
                                        )
                                        if (searchState.value.isEmpty()) {
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
                            onClick = { (activity as MainActivity).pushStack(BrandDetail.newInstance(null))},
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.colorOrange)),
                            modifier = Modifier
                                .padding(vertical = 16.dp)
                                .padding(end = 4.dp)
                                .wrapContentWidth()
                                .wrapContentHeight()
                        ) {

                            Text(
                                text = "Create Brand",
                                style = Typography.h2.copy(color = AppColor.WhiteText),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(horizontal = 4.dp)
                            )
                        }
                    }
                }
                LazyVerticalGrid(columns = GridCells.Fixed(3), modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 8.dp), content = {
                    items(brands.size) {
                        BrandItem(brands[it]){brand->
                            (activity as MainActivity).pushStack(BrandDetail.newInstance(brand))
                        }
                    }
                })
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun BrandItem(brand: Brand, onClick: (Brand) -> Unit) {
        Surface(
            onClick = { onClick(brand)}, modifier = Modifier.padding(end = 16.dp, bottom = 16.dp),
            elevation = 8.dp,
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CoilImageLoader(
                    url = brand.image,
                    placeHolder = R.drawable.ic_placeholder_image,
                    modifier = Modifier
                        .size(160.dp)
                        .padding(vertical = 24.dp, horizontal = 24.dp)
                )
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = brand.name,
                        style = Typography.h1.copy(
                            color = AppColor.Black,
                        ),
                        textAlign = TextAlign.Center,
                    )

                    Text(
                        text = "12",
                        style = Typography.body2.copy(
                            color = AppColor.BodyText,
                        ),
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }

    private fun InitFragment(){
        mainActivity?.delegate = object : MainActivity.Delegate{
            override fun popBack() {
                viewModel.getBrand()
            }
        }
    }
}