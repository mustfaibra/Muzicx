package com.example.muzicx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.graphics.toArgb
import com.example.muzicx.screen.HolderPage
import com.example.muzicx.ui.theme.black
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        window.statusBarColor = black.toArgb()
        super.onCreate(savedInstanceState)
        setContent {
            HolderPage()
        }
    }
}