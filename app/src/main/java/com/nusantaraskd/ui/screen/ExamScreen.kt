package com.nusantaraskd.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamScreen(onFinish: () -> Unit) {
    var selectedOption by remember { mutableStateOf("") }
    var currentIndex by remember { mutableIntStateOf(1) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("TWK • Soal $currentIndex / 110") },
                actions = { Text("01:40:00", modifier = Modifier.padding(end = 16.dp)) }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp).fillMaxSize(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Text("Berikut ini adalah isi teks pertanyaan dummy untuk simulasi CAT SKD Nusantara.", modifier = Modifier.padding(16.dp))
            }
            val options = listOf("A", "B", "C", "D", "E")
            options.forEach { opt ->
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = selectedOption == opt, onClick = { selectedOption = opt })
                    Text("Pilihan jawaban $opt")
                }
            }
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = false, onCheckedChange = {})
                Text("Tandai Ragu-ragu")
            }
            Spacer(modifier = Modifier.weight(1f))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(onClick = { if (currentIndex > 1) currentIndex-- }, modifier = Modifier.weight(1f)) {
                    Text("Sebelumnya")
                }
                Button(onClick = {
                    if (currentIndex < 110) currentIndex++ else onFinish()
                }, modifier = Modifier.weight(1f)) {
                    Text("Simpan & Lanjut")
                }
            }
        }
    }
}
