package com.nusantaraskd.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.nusantaraskd.ui.theme.NusantaraSKDTheme
import com.nusantaraskd.ui.screen.AppNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NusantaraSKDTheme {
                AppNavigation()
            }
        }
    }
}
