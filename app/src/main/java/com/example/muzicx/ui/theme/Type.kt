package com.example.muzicx.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.muzicx.R

// Set of Material typography styles to start with

val family = FontFamily(
        Font(R.font.rubik_light,weight = FontWeight.Light, FontStyle.Normal),
        Font(R.font.rubik_regular,weight = FontWeight.Normal, FontStyle.Normal),
        Font(R.font.rubik_bold,weight = FontWeight.Bold, FontStyle.Normal),
        Font(R.font.rubic_extra_bold,weight = FontWeight.ExtraBold, FontStyle.Normal)
)
val typography = Typography(
        family,
)