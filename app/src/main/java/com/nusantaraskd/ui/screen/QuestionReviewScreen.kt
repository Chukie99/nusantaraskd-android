package com.nusantaraskd.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nusantaraskd.data.local.entity.QuestionEntity

@Composable
fun QuestionReviewScreen(
    question: QuestionEntity,
    userAnswer: String,
    onNext: () -> Unit
) {
    val isCorrect = userAnswer == question.correctAnswer
    val isTkp = question.category == "TKP"

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Pembahasan Soal", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        // Status Jawaban
        Card(
            colors = CardDefaults.cardColors(
                containerColor = if (isCorrect || isTkp) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
            ),
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = if (isTkp) "Analisis TKP (Bobot 1-5)" else (if (isCorrect) "Jawaban Benar! ✓" else "Jawaban Salah ✗"),
                    style = MaterialTheme.typography.titleMedium,
                    color = if (isCorrect || isTkp) Color(0xFF2E7D32) else Color(0xFFC62828)
                )
                Text(text = "Jawaban Anda: $userAnswer")
                if (!isCorrect && !isTkp) {
                    Text(text = "Kunci Jawaban: ${question.correctAnswer}", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }

        // Pembahasan
        LazyColumn(modifier = Modifier.weight(1f)) {
            item {
                Text(text = "Pembahasan:", style = MaterialTheme.typography.titleSmall)
                Text(text = question.explanation, style = MaterialTheme.typography.bodyMedium)
            }
        }

        Button(onClick = onNext, modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {
            Text("Lanjut ke Soal Berikutnya")
        }
    }
}
