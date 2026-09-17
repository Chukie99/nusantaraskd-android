package com.nusantaraskd.ui.screen

import android.content.Context
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivationViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow<ActivationState>(ActivationState.Idle)
    val uiState: StateFlow<ActivationState> = _uiState.asStateFlow()

    fun activate(key: String) {
        if (key.isBlank()) {
            _uiState.value = ActivationState.Error("License key cannot be blank")
            return
        }

        viewModelScope.launch {
            _uiState.value = ActivationState.Loading
            val deviceId = Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            ) ?: "unknown_device"
            
            // TODO: Replace with actual license repository call when Firebase is ready
            // For now, simulate successful activation
            val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            prefs.edit().putBoolean("is_activated", true).apply()
            
            _uiState.value = ActivationState.Success
        }
    }
}

sealed class ActivationState {
    object Idle : ActivationState()
    object Loading : ActivationState()
    object Success : ActivationState()
    data class Error(val message: String) : ActivationState()
}

@Composable
fun ActivationScreen(
    onActivated: () -> Unit,
    viewModel: ActivationViewModel = hiltViewModel()
) {
    var licenseKey by remember { mutableStateOf("") }
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(state) {
        if (state is ActivationState.Success) {
            onActivated()
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Aktivasi Aplikasi",
                style = MaterialTheme.typography.headlineMedium
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Masukkan lisensi Anda untuk melanjutkan",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = licenseKey,
                onValueChange = { licenseKey = it },
                label = { Text("License Key") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.activate(licenseKey) },
                modifier = Modifier.fillMaxWidth(),
                enabled = state !is ActivationState.Loading
            ) {
                if (state is ActivationState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Aktivasi Sekarang")
                }
            }

            if (state is ActivationState.Error) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = (state as ActivationState.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
