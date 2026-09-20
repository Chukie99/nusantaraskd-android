package com.nusantaraskd.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nusantaraskd.data.room.QuestionEntity

@Composable
fun ExamSummaryScreen(
    scoreTwk: Int,
    scoreTiu: Int,
    scoreTkp: Int,
    questions: List<QuestionEntity>,
    userAnswers: Map<Int, String>, // Index soal -> Jawaban user (A/B/C/D/E)
    onBackHome: () -> Unit
) {
    val totalScore = scoreTwk + scoreTiu + scoreTkp
    // Passing Grade SKD CPNS: TWK 65, TIU 80, TKP 166
    val isPassed = scoreTwk >= 65 && scoreTiu >= 80 && scoreTkp >= 166

    var showReview by remember { mutableStateOf(false) }

    if (!showReview) {
        // Layar Rekap Skor Akhir
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Hasil Simulasi SKD CPNS", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isPassed) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
                )
            ) {
                Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = if (isPassed) "LULUS PASSING GRADE" else "BELUM LULUS",
                        style = MaterialTheme.typography.titleLarge,
                        color = if (isPassed) Color(0xFF2E7D32) else Color(0xFFC62828)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Total Skor: $totalScore", style = MaterialTheme.typography.headlineSmall)
                }
            }

            Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Rincian Nilai per Materi:")
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("• TWK (Min. 65): $scoreTwk")
                    Text("• TIU (Min. 80): $scoreTiu")
                    Text("• TKP (Min. 166): $scoreTkp")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { showReview = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Lihat Pembahasan Soal")
            }

            OutlinedButton(
                onClick = onBackHome,
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            ) {
                Text("Kembali ke Menu Utama")
            }
        }
    } else {
        // Layar Daftar Pembahasan Semua Soal
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Pembahasan Soal", style = MaterialTheme.typography.headlineSmall)
                Button(onClick = { showReview = false }) {
                    Text("Tutup Pembahasan")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(modifier = Modifier.weight(1f)) {
                itemsIndexed(questions) { index, q ->
                    val userAns = userAnswers[index] ?: "-"
                    val isTkp = q.category == "TKP"
                    val isCorrect = userAns == q.correctAnswer

                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isTkp) MaterialTheme.colorScheme.surfaceVariant
                            else if (isCorrect) Color(0xFFF1F8E9) else Color(0xFFFFEBEE)
                        )
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(text = "Soal ${index + 1} [${q.category}]", style = MaterialTheme.typography.labelMedium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = q.questionText, style = MaterialTheme.typography.bodyMedium)
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Text(text = "Jawaban Anda: $userAns", style = MaterialTheme.typography.bodySmall)
                            if (!isTkp) {
                                Text(text = "Kunci Jawaban: ${q.correctAnswer}", style = MaterialTheme.typography.bodySmall, color = Color(0xFF2E7D32))
                            } else {
                                Text(text = "Sistem Bobot TKP (1-5 aktif)", style = MaterialTheme.typography.bodySmall, color = Color(0xFF1565C0))
                            }

                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "Pembahasan:", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                            Text(text = q.explanation, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}
