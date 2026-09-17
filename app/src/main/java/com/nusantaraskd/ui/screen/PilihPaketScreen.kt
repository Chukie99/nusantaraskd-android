package com.nusantaraskd.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
fun PilihPaketScreen(onBack: () -> Unit, onSelect: (String) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pilih Paket Simulasi") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) } }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp).fillMaxSize(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            PaketCard("Simulasi SKD Penuh", "110 soal, 100 menit", true) { onSelect("full") }
            PaketCard("Mini Tryout", "50 soal, 45 menit", false) { onSelect("mini") }
            PaketCard("Latihan TWK", "30 soal, tanpa timer", false) { onSelect("twk") }
            PaketCard("Latihan TIU", "35 soal, tanpa timer", false) { onSelect("tiu") }
            PaketCard("Latihan TKP", "45 soal, tanpa timer", false) { onSelect("tkp") }
        }
    }
}

@Composable
fun PaketCard(title: String, desc: String, bold: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        border = if (bold) androidx.compose.foundation.BorderStroke(2.dp, Color(0xFF5C8D89)) else null
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontWeight = FontWeight.Bold)
            Text(desc, color = Color.Gray)
        }
    }
}
