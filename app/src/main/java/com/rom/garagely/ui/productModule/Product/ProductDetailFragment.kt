package com.rom.garagely.ui.productModule.Product

import AppColor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material.DropdownMenu
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import com.rom.garagely.MainActivity
import com.rom.garagely.R
import com.rom.garagely.common.PreferencesManager
import com.rom.garagely.composeView.CoilImageLoader
import com.rom.garagely.constant.IntentKey
import com.rom.garagely.constant.SharedPreferenceKeys
import com.rom.garagely.model.Car
import com.rom.garagely.model.Key
import com.rom.garagely.theme.Typography
import com.rom.garagely.ui.base.BaseComposeFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID

@AndroidEntryPoint
class ProductDetailFragment : BaseComposeFragment() {

    private var image: Uri? = null

    companion object {
        fun newInstance(car: Car?): ProductDetailFragment {
            return ProductDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(IntentKey.CAR, car)
                }
            }
        }
    }

    private val viewModel: ProductDetailViewModel by viewModels()
    private var car: Car? = null
    var delegate: Delegate? = null


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        car = arguments?.getParcelable(IntentKey.CAR, Car::class.java)
        viewModel.setCar(car)
    }

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
        val carUi = viewModel.car.collectAsState()

        val nameState = remember {
            mutableStateOf(carUi.value?.name ?: "")
        }
        val brand = remember {
            mutableStateOf(carUi.value?.brand ?: "")
        }

        val model = remember {
            mutableStateOf(carUi.value?.model ?: "")
        }

        val productInf = remember {
            mutableStateOf(carUi.value?.info ?: "")
        }
        val priceUi = remember {
            mutableStateOf(carUi.value?.price ?: 0.0)
        }

        val quantityUi = remember {
            mutableStateOf(carUi.value?.quantity ?: 0)
        }
        val imageUrl = remember {
            mutableStateOf((carUi.value?.image)?.toUri())
        }

        val keys = remember {
            (carUi.value?.keys ?: listOf()).toMutableStateList()
        }

        var expand by remember {
            mutableStateOf(false)
        }
        val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri ->
                image = uri
                uri?.let {
                    imageUrl.value = uri
                }
            }
        )

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
                            .size(160.dp, 160.dp)
                            .padding(top = 24.dp)
                            .clip(shape = RoundedCornerShape(8.dp))
                    ) {
                        CoilImageLoader(
                            url = imageUrl.value,
                            placeHolder = R.drawable.ic_placeholder_image,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.clickable {
                                expand = !expand
                            }
                        )
                    }
                    AppDropdownMenu(
                        items = listOf("Take new photo", "Choose Photo", "Delete"),
                        expand = expand,
                        offset = DpOffset(0.dp, 0.dp),
                        modifier = Modifier,
                        onDismiss = { expand = !expand },
                        onItemClick = {
                            if (it == "Delete") {
                                imageUrl.value = null
                            } else {
                                singlePhotoPickerLauncher.launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                            }
                        }
                    )

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
                        value = priceUi.value.toString(),
                        onValueChange = {
                            priceUi.value = it.toDouble()
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
                            value = brand.value,
                            onValueChange = {
                                brand.value = it
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
                            value = model.value,
                            onValueChange = {
                                model.value = it
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
                        value = productInf.value,
                        onValueChange = {
                            productInf.value = it
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
                                    .weight(1f),
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
                                    .padding(top = 24.dp),
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
                            style = Typography.h2,
                            modifier = Modifier.padding(top = 24.dp)
                        )
                        Row(
                            modifier = Modifier.padding(24.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_minus),
                                contentDescription = null,
                                modifier = Modifier.clickable {
                                    if (quantityUi.value > 0) {
                                        quantityUi.value--
                                    } else {
                                        return@clickable
                                    }
                                }
                            )
                            Text(
                                modifier = Modifier.padding(16.dp),
                                text = quantityUi.value.toString(),
                                style = Typography.h2
                            )
                            Image(
                                painter = painterResource(id = R.drawable.plus_fill),
                                contentDescription = null,
                                Modifier.clickable {
                                    quantityUi.value++
                                }
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
                    keys.forEachIndexed { index, key ->
                        ItemKey(key = key) { updateKey, isRemove ->
                            if (!isRemove) {
                                keys[index] = updateKey
                            } else {
                                keys.remove(updateKey)
                            }
                        }
                    }
                    ItemKey(key = Key(), true) { key, isNew ->
                        if (isNew) {
                            keys.add(key)
                        }

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
                            if (carUi.value == null) {
                                val newCar = Car().apply {
                                    id = UUID.randomUUID().toString()
                                    name = nameState.value
                                    account_id = PreferencesManager.instance.get(SharedPreferenceKeys.USER_ID)
                                    this.info = productInf.value
                                    this.model = model.value
                                    this.price = priceUi.value
                                    this.quantity = quantityUi.value
                                    this.keys = keys.filter { it.name.isNotEmpty() }.toMutableList()
                                }
                                delegate!!.onUpdateCare()
                                viewModel.createProduct(newCar, image)
                            } else {
                                this@ProductDetailFragment.car?.apply {
                                    this.name = nameState.value
                                    this.brand = brand.value
                                    this.info = productInf.value
                                    this.model = model.value
                                    this.price = priceUi.value
                                    this.quantity = quantityUi.value
                                    this.keys = keys.filter { it.name.isNotEmpty() }.toMutableList()
                                }
                                viewModel.setCar(car)
                                viewModel.createProduct(car = car!!, image)

                            }

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
                            text = "Create".takeIf { carUi.value == null } ?: "Update",
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
    fun ItemKey(key: Key, isNew: Boolean = false, onItemChange: (Key, Boolean) -> Unit) {

        val keyState = remember {
            mutableStateOf(key)
        }
        val text = remember {
            mutableStateOf(key.name)
        }

        val price = remember {
            mutableStateOf(key.price)
        }


        Row(
            modifier = Modifier
                .padding(bottom = 12.dp)
                .fillMaxWidth(0.7f),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(id = R.drawable.plus_fill.takeIf { key.name.isEmpty() && isNew }
                    ?: R.drawable.ic_minus), contentDescription = null,
                modifier = Modifier.clickable {
                    onItemChange.invoke(key, true)
                }
            )
            OutlinedTextField(
                value = text.value,
                onValueChange = {
                    text.value = it
                    keyState.value.name = it
                    onItemChange.invoke(keyState.value, false)

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
                value = price.value.toString(),
                onValueChange = {
                    price.value = it.toDouble()
                    keyState.value.price = it.toDouble()
                    onItemChange.invoke(keyState.value, false)
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

    @Composable
    fun AppDropdownMenu(
        items: List<String>,
        expand: Boolean,
        offset: DpOffset,
        modifier: Modifier,
        onDismiss: () -> Unit,
        onItemClick: (String) -> Unit
    ) {
        DropdownMenu(expanded = expand, offset = offset, onDismissRequest = onDismiss) {
            items.forEach {
                Text(
                    text = it,
                    modifier = modifier.clickable {
                        onItemClick.invoke(it)
                        onDismiss.invoke()
                    }
                )
            }
        }
    }

    interface Delegate {
        fun onUpdateCare()
    }
}