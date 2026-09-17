package com.nusantaraskd.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamInstructionScreen(onStart: () -> Unit, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Konfirmasi Simulasi") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) } }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp).fillMaxSize(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Konfigurasi SKD CAT", fontWeight = FontWeight.Bold)
                    Text("Jumlah Soal: 110 Soal")
                    Text("Durasi: 100 Menit")
                    Text("Komposisi: TWK (30), TIU (35), TKP (45)")
                    Text("Passing Grade: TWK 65, TIU 80, TKP 166")
                }
            }
            Surface(modifier = Modifier.fillMaxWidth(), color = Color(0xFFFFF3CD), shape = RoundedCornerShape(12.dp)) {
                Text("Timer tidak dapat dijeda. Pastikan Anda siap.", modifier = Modifier.padding(16.dp), color = Color(0xFF856404))
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = onStart, modifier = Modifier.fillMaxWidth().height(50.dp), shape = RoundedCornerShape(12.dp)) {
                Text("MULAI SEKARANG")
            }
            OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth().height(50.dp), shape = RoundedCornerShape(12.dp)) {
                Text("Batal")
            }
        }
    }
}
