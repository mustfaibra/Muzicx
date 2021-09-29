package com.example.muzicx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
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
            val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current){
                "No current owner was provided"
            }
            CompositionLocalProvider(
                LocalViewModelStoreOwner provides viewModelStoreOwner
            ) {
                HolderPage()
            }
        }
    }
}