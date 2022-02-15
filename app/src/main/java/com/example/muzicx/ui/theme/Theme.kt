package com.example.muzicx.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = orange,
    background = black,
    onPrimary = black,
    onBackground = white,
    secondaryVariant = Color.Gray,
    secondary = dark,

)

@Composable
fun MuzicxTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = DarkColorPalette

    MaterialTheme(
            colors = colors,
            typography = typography,
            shapes = Shapes,
            content = content
    )
}