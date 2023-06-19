package com.rom.garagely.composeView

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest


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
