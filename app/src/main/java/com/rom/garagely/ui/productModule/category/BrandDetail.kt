package com.rom.garagely.ui.productModule.category

import AppColor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.res.colorResource
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
import com.rom.garagely.constant.Constant.BRAND
import com.rom.garagely.constant.SharedPreferenceKeys
import com.rom.garagely.model.Brand
import com.rom.garagely.model.Car
import com.rom.garagely.theme.Typography
import com.rom.garagely.ui.base.BaseComposeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BrandDetail : BaseComposeFragment() {
    companion object {
        fun newInstance(car: Brand?): BrandDetail {
            return BrandDetail().apply {
                arguments = Bundle().apply {
                    putParcelable(BRAND, car)
                }
            }
        }
    }

    private val viewModel: BrandDetailViewModel by viewModels()
    private var brand: Brand? = null
    private var image: Uri? = null
    private var isDelete: Boolean = false

    private val mainActivity : MainActivity?
        get() = activity as? MainActivity


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        brand = arguments?.getParcelable(BRAND, Brand::class.java)
        viewModel.setBrand(brand)
    }

    @Composable
    override fun ContentView() {
        BrandDetailView()
        if (viewModel.result.collectAsState().value) {
            (activity as MainActivity).popStack()
        }
    }

    @Composable
    fun BrandDetailView() {

        val brandUi by viewModel.brand.collectAsState()

        val imageUrl = remember {
            mutableStateOf(brandUi?.image?.toUri())
        }


        var offset = DpOffset(0.dp, 0.dp)
        var expand by remember {
            mutableStateOf(false)
        }
        val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri ->
                image = uri
                uri?.let {
                    imageUrl.value = uri
                    isDelete = false
                }
            }
        )
        val nameState = remember {
            mutableStateOf(brandUi?.name ?: "")
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppColor.Background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box {
                    CoilImageLoader(
                        url = imageUrl.value,
                        placeHolder = R.drawable.ic_placeholder_image,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(160.dp)
                            .padding(top = 24.dp)
                            .clip(shape = RoundedCornerShape(8.dp))
                            .clickable {
                                expand = !expand
                            }
                            .onGloballyPositioned {
                                offset =
                                    DpOffset(x = it.positionInRoot().x.dp, y = 0.dp)
                            }
                    )
                    AppDropdownMenu(
                        items = listOf("Take new photo", "Choose Photo", "Delete"),
                        expand = expand,
                        offset = offset,
                        modifier = Modifier,
                        onDismiss = { expand = !expand },
                        onItemClick = {
                            if (it == "Delete") {
                                isDelete =  true
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
                }

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
                text = "Car",
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
            if(brandUi != null) {
                Button(
                    onClick = { viewModel.delete(brandUi!!) },
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
                        if (brandUi == null) {
                            val brand = Brand().apply {
                                name = nameState.value
                                account_id =
                                    PreferencesManager.instance.get(SharedPreferenceKeys.USER_ID)
                            }
                            viewModel.createBrand(brand, imageUrl.value)

                        } else {
                            this@BrandDetail.brand!!.apply {
                                this.name = nameState.value
                            }
                            viewModel.createBrand(brand!!, image, isDelete)

                        }
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.colorOrange)),
                    modifier = Modifier
                        .padding(vertical = 24.dp)
                        .padding(end = 4.dp)
                        .wrapContentWidth()
                        .wrapContentHeight()
                ) {
                    Text(
                        text = "Create".takeIf { brandUi == null } ?: "Update",
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

@Composable
fun ItemCarSelected(car: Car?) {
    Column(Modifier.fillMaxWidth()) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = painterResource(id = R.drawable.ic_minus), contentDescription = null)
            Text(
                text = car?.name ?: "TOYOTA",
                style = Typography.body1,
                modifier = Modifier.padding(start = 24.dp)
            )
        }
        Divider(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(color = AppColor.Line)
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