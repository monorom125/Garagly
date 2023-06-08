package com.rom.garagely.theme


import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val headTextStyle = TextStyle(
    fontFamily = Poppins ,
    fontWeight = FontWeight.Medium,
    color = Color.Black
)

val subTitleTextStyle = TextStyle(
    fontFamily = Poppins,
    fontWeight = FontWeight.Normal,
    color = Color.Black
)

val bodyTextStyle = TextStyle(
    fontFamily = Poppins,
    fontWeight = FontWeight.Normal,
    color = Color.Black
)

val buttonTextStyle = TextStyle(
    fontFamily = Poppins,
    fontWeight = FontWeight.SemiBold,
    color = Color.White
)

//val receiptTextStyle = TextStyle(
//    fontFamily = CourierPrime,
//    fontWeight = FontWeight.Normal,
//    color = Color.BodyText
//)

//@Composable
//fun switchStyle(): SwitchColors {
//    return SwitchDefaults.colors(
//        checkedThumbColor = C.White,
//        uncheckedThumbColor = AppColor.White,
//        checkedTrackColor = AppColor.Green,
//        checkedTrackAlpha = 1f,
//        uncheckedTrackColor = AppColor.Gray
//    )
//}
//
//@Composable
//fun disableSwitchStyle(): SwitchColors {
//    return SwitchDefaults.colors(
//        checkedThumbColor = AppColor.White,
//        uncheckedThumbColor = AppColor.White,
//        checkedTrackColor = AppColor.Green,
//        checkedTrackAlpha = 0.36f,
//        uncheckedTrackColor = AppColor.Gray
//    )


val Typography = Typography(
    h1 = headTextStyle.copy(fontSize = 18.sp),
    h2 = headTextStyle.copy(fontSize = 16.sp),
    h3 = headTextStyle.copy(fontSize = 14.sp),
    subtitle1 = subTitleTextStyle.copy(fontSize = 16.sp),
    subtitle2 = subTitleTextStyle.copy(fontSize = 14.sp),
    body1 = bodyTextStyle.copy(fontSize = 16.sp, color = AppColor.BodyText),
    body2 = bodyTextStyle.copy(fontSize = 14.sp, color = AppColor.BodyText),
    button = buttonTextStyle.copy(fontSize = 16.sp),
    caption = subTitleTextStyle.copy(fontSize = 12.sp),
    overline = bodyTextStyle.copy(fontSize = 12.sp)
)