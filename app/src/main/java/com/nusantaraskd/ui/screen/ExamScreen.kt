package com.nusantaraskd.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.nusantaraskd.data.room.QuestionEntity
import kotlinx.coroutines.delay

@Composable
fun ExamScreen(
    questions: List<QuestionEntity>,
    onFinishExam: (scoreTwk: Int, scoreTiu: Int, scoreTkp: Int, userAnswers: Map<Int, String>) -> Unit
) {
    var timeLeftSeconds by remember { mutableStateOf(6000) } // 100 Menit
    var currentIndex by remember { mutableStateOf(0) }
    
    val userAnswers = remember { mutableStateMapOf<Int, String>() }
    // Saran #2: State untuk menandai soal ragu-ragu (flagging)
    val flaggedQuestions = remember { mutableStateMapOf<Int, Boolean>() }

    LaunchedEffect(key1 = timeLeftSeconds) {
        if (timeLeftSeconds > 0) {
            delay(1000L)
            timeLeftSeconds--
        } else {
            val (twk, tiu, tkp) = calculateScores(questions, userAnswers)
            onFinishExam(twk, tiu, tkp, userAnswers)
        }
    }

    val minutes = timeLeftSeconds / 60
    val seconds = timeLeftSeconds % 60
    val currentQuestion = questions.getOrNull(currentIndex)
    val isFlagged = flaggedQuestions[currentIndex] == true

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Header: Timer & Indikator Ragu-ragu
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Waktu: %02d:%02d".format(minutes, seconds), color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.titleMedium)
            
            // Tombol Ragu-ragu (Saran #2)
            OutlinedButton(
                onClick = { flaggedQuestions[currentIndex] = !isFlagged },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (isFlagged) Color(0xFFFFE082) else Color.Transparent
                )
            ) {
                Text(if (isFlagged) "Ragu-ragu ✓" + "" else "Tandai Ragu")
            }

            Text(text = "Soal ${currentIndex + 1} / ${questions.size}", style = MaterialTheme.typography.titleMedium)
        }

        // Body Soal
        if (currentQuestion != null) {
            Column(modifier = Modifier.weight(1f).padding(vertical = 16.dp)) {
                Text(text = "[${currentQuestion.category}] ${currentQuestion.questionText}", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(16.dp))

                val options = listOf(
                    "A" to currentQuestion.optionA,
                    "B" to currentQuestion.optionB,
                    "C" to currentQuestion.optionC,
                    "D" to currentQuestion.optionD,
                    "E" to currentQuestion.optionE
                )

                options.forEach { (key, value) ->
                    val isSelected = userAnswers[currentIndex] == key
                    Button(
                        onClick = { userAnswers[currentIndex] = key },
                        colors = if (isSelected) ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary) 
                                 else ButtonDefaults.outlinedButtonColors(),
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                    ) {
                        Text(text = "$key. $value")
                    }
                }
            }
        }

        // Footer Navigasi
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                enabled = currentIndex > 0,
                onClick = { if (currentIndex > 0) currentIndex-- }
            ) {
                Text("Sebelumnya")
            }

            Button(
                onClick = {
                    if (currentIndex < questions.size - 1) {
                        currentIndex++
                    } else {
                        val (twk, tiu, tkp) = calculateScores(questions, userAnswers)
                        onFinishExam(twk, tiu, tkp, userAnswers)
                    }
                }
            ) {
                Text(if (currentIndex == questions.size - 1) "Selesai & Kumpul" else "Selanjutnya")
            }
        }
    }
}

private fun calculateScores(questions: List<QuestionEntity>, userAnswers: Map<Int, String>): Triple<Int, Int, Int> {
    var twk = 0
    var tiu = 0
    var tkp = 0

    questions.forEachIndexed { index, q ->
        val ans = userAnswers[index]
        if (ans != null) {
            when (q.category) {
                "TWK" -> if (ans == q.correctAnswer) twk += 5
                "TIU" -> if (ans == q.correctAnswer) tiu += 5
                "TKP" -> {
                    val weight = when (ans) {
                        "A" -> q.weightA
                        "B" -> q.weightB
                        "C" -> q.weightC
                        "D" -> q.weightD
                        "E" -> q.weightE
                        else -> 0
                    }
                    tkp += weight
                }
            }
        }
    }
    return Triple(twk, tiu, tkp)
}
