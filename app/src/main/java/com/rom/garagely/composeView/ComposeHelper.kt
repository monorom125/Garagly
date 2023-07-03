package com.rom.garagely.composeView

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.rom.garagely.model.Brand
import com.rom.garagely.model.Discount
import com.rom.garagely.model.Tax


@Composable
fun AppOutlineTextField(){

}@Composable
fun CoilImageLoader(
    url: Any?,
    placeHolder: Int,
    modifier: Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .decoderFactory(SvgDecoder.Factory())
            .error(placeHolder)
            .build(),
        placeholder = painterResource(placeHolder),
        contentDescription = null,
        contentScale = contentScale,
        modifier = modifier
    )
}
@Composable
fun ItemDropdownMenu(
    items: List<Brand>,
    expand: Boolean,
    offset: DpOffset = DpOffset.Zero,
    modifier: Modifier,
    onDismiss: () -> Unit,
    onItemClick: (Brand) -> Unit
) {
    DropdownMenu(expanded = expand, offset = offset, onDismissRequest = onDismiss) {
        items.forEach {
            Text(
                text = it.name?: "",
                modifier = modifier.clickable {
                    onItemClick.invoke(it)
                    onDismiss.invoke()
                }
                    .padding(horizontal = 24.dp)
            )
        }
    }
}


@Composable
fun TaxDropdownMenu(
    items: List<Tax>,
    expand: Boolean,
    offset: DpOffset = DpOffset.Zero,
    modifier: Modifier,
    onDismiss: () -> Unit,
    onItemClick: (Tax) -> Unit
) {
    DropdownMenu(expanded = expand, offset = offset, onDismissRequest = onDismiss) {
        items.forEach {
            Text(
                text = it.name?: "",
                modifier = modifier.clickable {
                    onItemClick.invoke(it)
                    onDismiss.invoke()
                }
                    .padding(horizontal = 24.dp)
            )
        }
    }
}

