package com.nusantaraskd.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onStartExam: () -> Unit,
    onNavigateMenu: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nusantara SKD", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF0F2440), titleContentColor = Color.White)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFFAF7F2))
                .padding(16.dp)
        ) {
            // Greeting Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF5C8D89))
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("Selamat Belajar,", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
                    Text("Pejuang CASN", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Target Skor: 345 / 400", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Big Start Exam Button
            Button(
                onClick = onStartExam,
                modifier = Modifier.fillMaxWidth().height(60.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F2440))
            ) {
                Text("🚀 MULAI SIMULASI CAT", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Menu Utama", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F2440))
            Spacer(modifier = Modifier.height(12.dp))

            // Grid 2x2 Menu
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    MenuCard("Mode Latihan", Modifier.weight(1f)) { onNavigateMenu("practice") }
                    MenuCard("Bank Soal Salah", Modifier.weight(1f)) { onNavigateMenu("weakness") }
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    MenuCard("Analisis Skor", Modifier.weight(1f)) { onNavigateMenu("stats") }
                    MenuCard("Riwayat Ujian", Modifier.weight(1f)) { onNavigateMenu("history") }
                }
            }
        }
    }
}

@Composable
fun MenuCard(title: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .height(100.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .clickable(onClick = onClick)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F2440))
    }
}
