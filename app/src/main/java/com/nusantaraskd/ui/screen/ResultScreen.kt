package com.nusantaraskd.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(score: Int, twk: Int, tiu: Int, tkp: Int, passed: Boolean, onHome: () -> Unit) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Hasil Simulasi") }) }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp).fillMaxSize(), verticalArrangement = Arrangement.spacedBy(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
                Column(modifier = Modifier.padding(24.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("SKOR TOTAL", color = Color.Gray)
                    Text("$score", fontSize = 48.sp, fontWeight = FontWeight.Bold, color = Color(0xFF5C8D89))
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(color = if (passed) Color(0xFFA8E6CF) else Color(0xFFFFD3D3), shape = RoundedCornerShape(8.dp)) {
                        Text(
                            if (passed) "MEMENUHI PASSING GRADE" else "BELUM LULUS",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            fontWeight = FontWeight.Bold,
                            color = if (passed) Color(0xFF155724) else Color(0xFF721C24)
                        )
                    }
                }
            }
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Breakdown Skor", fontWeight = FontWeight.Bold)
                    Text("TWK: $twk (Passing Grade: 65) - ${if (twk >= 65) "Lulus" else "Tidak Lulus"}")
                    Text("TIU: $tiu (Passing Grade: 80) - ${if (tiu >= 80) "Lulus" else "Tidak Lulus"}")
                    Text("TKP: $tkp (Passing Grade: 166) - ${if (tkp >= 166) "Lulus" else "Tidak Lulus"}")
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = {}, modifier = Modifier.fillMaxWidth().height(50.dp), shape = RoundedCornerShape(12.dp)) {
                Text("Pembahasan Lengkap")
            }
            OutlinedButton(onClick = onHome, modifier = Modifier.fillMaxWidth().height(50.dp), shape = RoundedCornerShape(12.dp)) {
                Text("Kembali ke Beranda")
            }
        }
    }
}
