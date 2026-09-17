package com.nusantaraskd.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamScreen(
    viewModel: ExamViewModel = hiltViewModel(),
    onFinish: (Int, Int, Int, Int, Boolean) -> Unit
) {
    val questions by viewModel.questions.collectAsState()
    Log.d("EXAM_SCREEN", "ExamScreen composed, questions: ${questions.size}")
    val currentIndex by viewModel.currentIndex.collectAsState()
    val answers by viewModel.answers.collectAsState()
    val markedQuestions by viewModel.markedQuestions.collectAsState()
    val timeRemaining by viewModel.timeRemaining.collectAsState()

    var showNavigator by remember { mutableStateOf(false) }

    val currentQuestion = questions.getOrNull(currentIndex)
    val selectedOption = answers[currentIndex] ?: ""
    val isMarked = markedQuestions.contains(currentIndex)

    val hours = timeRemaining / 3600
    val minutes = (timeRemaining % 3600) / 60
    val seconds = timeRemaining % 60
    val timeFormatted = String.format("%02d:%02d:%02d", hours, minutes, seconds)
    val isWarning = timeRemaining < 300

    var submitted by remember { mutableStateOf(false) }

    // Auto submit on zero
    LaunchedEffect(timeRemaining) {
        if (timeRemaining <= 0 && !submitted && questions.isNotEmpty()) {
            submitted = true
            val res = viewModel.calculateResults()
            onFinish(res.totalScore, res.twkScore, res.tiuScore, res.tkpScore, res.passed)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("${currentQuestion?.category ?: "UJIAN"} • Soal ${currentIndex + 1} / ${questions.size}") },
                actions = {
                    Text(
                        timeFormatted,
                        color = if (isWarning) Color.Red else Color.Unspecified,
                        fontWeight = if (isWarning) FontWeight.Bold else FontWeight.Normal,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    IconButton(onClick = { showNavigator = true }) {
                        Icon(Icons.Filled.List, contentDescription = "Navigator")
                    }
                }
            )
        }
    ) { padding ->
        if (questions.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier.padding(padding).padding(16.dp).fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        currentQuestion?.questionText ?: "",
                        modifier = Modifier.padding(16.dp),
                        fontSize = 16.sp
                    )
                }

                val options = listOf(
                    "A" to (currentQuestion?.optionA ?: ""),
                    "B" to (currentQuestion?.optionB ?: ""),
                    "C" to (currentQuestion?.optionC ?: ""),
                    "D" to (currentQuestion?.optionD ?: ""),
                    "E" to (currentQuestion?.optionE ?: "")
                )

                options.forEach { (key, text) ->
                    if (text.isNotBlank()) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { viewModel.selectOption(key) },
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (selectedOption == key) Color(0xFFE2F0D9) else MaterialTheme.colorScheme.surface
                            ),
                            border = if (selectedOption == key) androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFF5C8D89)) else null
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp).fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(selected = (selectedOption == key), onClick = { viewModel.selectOption(key) })
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("$key. $text", fontSize = 14.sp)
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = isMarked, onCheckedChange = { viewModel.toggleMark() })
                    Text("Tandai Ragu-ragu")
                }

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = { viewModel.prevQuestion() },
                        enabled = currentIndex > 0,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Sebelumnya")
                    }

                    Button(
                        onClick = {
                            if (currentIndex < questions.size - 1) {
                                viewModel.nextQuestion()
                            } else {
                                val scope = CoroutineScope(Dispatchers.Main)
                                scope.launch {
                                    val res = viewModel.calculateResults()
                                    onFinish(res.totalScore, res.twkScore, res.tiuScore, res.tkpScore, res.passed)
                                }
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(if (currentIndex < questions.size - 1) "Simpan & Lanjut" else "SELESAIKAN")
                    }
                }
            }
        }

        if (showNavigator) {
            AlertDialog(
                onDismissRequest = { showNavigator = false },
                title = { Text("Navigator Soal") },
                text = {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(5),
                        modifier = Modifier.height(300.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(questions.size) { idx ->
                            val isAns = answers.containsKey(idx)
                            val isCur = idx == currentIndex
                            val isMrk = markedQuestions.contains(idx)

                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(
                                        color = when {
                                            isMrk -> Color(0xFFF4D06F)
                                            isAns -> Color(0xFF5C8D89)
                                            else -> Color.LightGray
                                        },
                                        shape = CircleShape
                                    )
                                    .border(
                                        width = if (isCur) 2.dp else 0.dp,
                                        color = Color.Black,
                                        shape = CircleShape
                                    )
                                    .clickable {
                                        viewModel.goToQuestion(idx)
                                        showNavigator = false
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${idx + 1}",
                                    color = if (isAns || isMrk) Color.White else Color.Black,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showNavigator = false }) {
                        Text("Tutup")
                    }
                }
            )
        }
    }
}
