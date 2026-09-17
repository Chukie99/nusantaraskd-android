package com.nusantaraskd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.nusantaraskd.ui.screen.AppNavGraph
import com.nusantaraskd.ui.theme.NusantaraSKDTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NusantaraSKDTheme {
                AppNavGraph()
            }
        }
    }
}
