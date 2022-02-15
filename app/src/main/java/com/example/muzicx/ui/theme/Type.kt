package com.example.muzicx.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.muzicx.R

// Set of Material typography styles to start with
val fonts = FontFamily(
        Font(resId = R.font.rubik_light,weight = FontWeight.Light,),
        Font(resId = R.font.rubik_regular,weight = FontWeight.Normal),
        Font(resId = R.font.rubik_bold,weight = FontWeight.Bold),
        Font(resId = R.font.rubic_extra_bold,weight = FontWeight.ExtraBold),
)

// Set of Material typography styles to start with
val typography = Typography(
        h1 = TextStyle(
                fontFamily = fonts,
                fontWeight = FontWeight.ExtraBold,
                fontSize = FontSize.xxl
        ),
        h2 = TextStyle(
                fontFamily = fonts,
                fontWeight = FontWeight.Bold,
                fontSize = FontSize.xl
        ),
        h3 = TextStyle(
                fontFamily = fonts,
                fontWeight = FontWeight.Bold,
                fontSize = FontSize.lg
        ),
        h4 = TextStyle(
                fontFamily = fonts,
                fontWeight = FontWeight.Bold,
                fontSize = FontSize.md
        ),
        h5 = TextStyle(
                fontFamily = fonts,
                fontWeight = FontWeight.Bold,
                fontSize = FontSize.sm
        ),
        body1 = TextStyle(
                fontFamily = fonts,
                fontWeight = FontWeight.Bold,
                fontSize = FontSize.md
        ),
        body2 = TextStyle(
                fontFamily = fonts,
                fontWeight = FontWeight.Normal,
                fontSize = FontSize.md
        ),
        button = TextStyle(
                fontFamily = fonts,
                fontWeight = FontWeight.Bold,
                fontSize = FontSize.md
        ),
        caption = TextStyle(
                fontFamily = fonts,
                fontWeight = FontWeight.Bold,
                fontSize = FontSize.sm
        ),
        subtitle1 = TextStyle(
                fontFamily = fonts,
                fontWeight = FontWeight.Bold,
                fontSize = FontSize.sm
        ),
        subtitle2 = TextStyle(
                fontFamily = fonts,
                fontWeight = FontWeight.Normal,
                fontSize = FontSize.sm
        ),
        defaultFontFamily = fonts,
)