package com.nusantaraskd.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nusantaraskd.ui.theme.Background
import com.nusantaraskd.ui.theme.Primary

@Composable
fun SplashScreen(onNavigateNext: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Nusantara SKD", style = MaterialTheme.typography.headlineLarge, color = Primary)
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator(color = Primary)
        }
    }
}