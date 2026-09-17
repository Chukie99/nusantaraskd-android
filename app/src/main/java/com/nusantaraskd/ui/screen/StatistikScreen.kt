package com.nusantaraskd.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.nusantaraskd.data.local.entity.ExamSessionEntity
import com.nusantaraskd.data.repository.ExamRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val examRepository: ExamRepository
) : ViewModel() {
    val sessions: StateFlow<List<ExamSessionEntity>> = examRepository.getAllSessionsFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}

data class StatsData(
    val totalExams: Int,
    val avgTotal: Int,
    val avgTwk: Int,
    val avgTiu: Int,
    val avgTkp: Int,
    val maxTotal: Int,
    val minTotal: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatistikScreen(
    onBack: () -> Unit,
    viewModel: StatsViewModel = hiltViewModel()
) {
    val sessions by viewModel.sessions.collectAsStateWithLifecycle()
    
    val stats = if (sessions.isEmpty()) {
        StatsData(0, 0, 0, 0, 0, 0, 0)
    } else {
        val total = sessions.sumOf { it.scoreTotal }
        val avg = total / sessions.size
        val max = sessions.maxOfOrNull { it.scoreTotal } ?: 0
        val min = sessions.minOfOrNull { it.scoreTotal } ?: 0
        val avgTwk = (sessions.sumOf { it.scoreTwk } / sessions.size)
        val avgTiu = (sessions.sumOf { it.scoreTiu } / sessions.size)
        val avgTkp = (sessions.sumOf { it.scoreTkp } / sessions.size)
        StatsData(sessions.size, avg, avgTwk, avgTiu, avgTkp, max, min)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Statistik Skor") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) } }
            )
        }
    ) { padding ->
        if (sessions.isEmpty()) {
            Box(
                modifier = Modifier.padding(padding).fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Belum ada data statistik", color = Color.Gray)
            }
        } else {
            LazyColumn(modifier = Modifier.padding(padding).padding(16.dp).fillMaxSize()) {
                item {
                    StatCard("Total Ujian", "${stats.totalExams} kali")
                    Spacer(modifier = Modifier.height(8.dp))
                    StatCard("Rata-rata Skor", "${stats.avgTotal}")
                    Spacer(modifier = Modifier.height(8.dp))
                    StatCard("Skor Tertinggi", "${stats.maxTotal}")
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Rata-rata Per Kategori", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    StatRow("TWK", stats.avgTwk)
                    Spacer(modifier = Modifier.height(4.dp))
                    StatRow("TIU", stats.avgTiu)
                    Spacer(modifier = Modifier.height(4.dp))
                    StatRow("TKP", stats.avgTkp)
                }
            }
        }
    }
}

@Composable
fun StatCard(label: String, value: String) {
    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(label, color = Color.Gray, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(value, fontWeight = FontWeight.Bold, fontSize = 24.sp, color = Color(0xFF5C8D89))
        }
    }
}

@Composable
fun StatRow(label: String, value: Int) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text("$label", color = Color.Gray, fontSize = 14.sp)
        Text("$value", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF5C8D89))
    }
}
