package com.nusantaraskd.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nusantaraskd.data.local.entity.ExamSessionEntity
import com.nusantaraskd.data.repository.ExamRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val examRepository: ExamRepository
) : ViewModel() {
    val sessions: StateFlow<List<ExamSessionEntity>> = flow {
        emit(examRepository.getAllSessions())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RiwayatScreen(
    onBack: () -> Unit
) {
    val viewModel = androidx.lifecycle.viewmodel.compose.viewModel<HistoryViewModel>()
    val sessions by viewModel.sessions.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Riwayat Ujian") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) } }
            )
        }
    ) { padding ->
        if (sessions.isEmpty()) {
            Box(
                modifier = Modifier.padding(padding).fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Info, null, modifier = Modifier.size(64.dp), tint = Color.Gray)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Belum ada riwayat ujian", color = Color.Gray)
                }
            }
        } else {
            LazyColumn(modifier = Modifier.padding(padding).padding(16.dp).fillMaxSize()) {
                items(sessions) { session ->
                    SessionCard(session)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun SessionCard(session: ExamSessionEntity) {
    val sdf = SimpleDateFormat("dd MMM yyyy HH:mm", Locale("id", "ID"))
    val dateStr = sdf.format(Date(session.startedAt))

    val badgeColor = if (session.passed) Color(0xFFA8E6CF) else Color(0xFFFFD3D3)
    val badgeText = if (session.passed) "LOLOS" else "BELUM"
    val badgeTextColor = if (session.passed) Color(0xFF155724) else Color(0xFF721C24)

    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(dateStr, fontSize = 14.sp, color = Color.Gray)
                Surface(color = badgeColor, shape = RoundedCornerShape(4.dp)) {
                    Text(badgeText, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), fontWeight = FontWeight.Bold, color = badgeTextColor, fontSize = 12.sp)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(session.type, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Skor Total: ${session.scoreTotal}", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color(0xFF5C8D89))
            Spacer(modifier = Modifier.height(4.dp))
            Text("TWK: ${session.scoreTwk} | TIU: ${session.scoreTiu} | TKP: ${session.scoreTkp}", fontSize = 14.sp, color = Color.Gray)
        }
    }
}
