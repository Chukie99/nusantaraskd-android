package com.nusantaraskd.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import android.util.Log
import com.nusantaraskd.data.repository.QuestionRepository

@Composable
fun SplashScreen(
    onNavigateNext: () -> Unit,
    questionRepository: QuestionRepository
) {
    LaunchedEffect(key1 = true) {
        delay(2000)
        questionRepository.seedIfEmpty()
        val count = questionRepository.getQuestionCount()
        Log.d("DB", "Total soal: $count")
        onNavigateNext()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAF7F2)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(Color(0xFF5C8D89), RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("N", color = Color.White, fontSize = 48.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text("Nusantara SKD", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F2440))
        Text("Simulasi CAT Mandiri", fontSize = 16.sp, color = Color(0xFF7895B2))
        Spacer(modifier = Modifier.height(32.dp))
        CircularProgressIndicator(color = Color(0xFF5C8D89))
    }
}
