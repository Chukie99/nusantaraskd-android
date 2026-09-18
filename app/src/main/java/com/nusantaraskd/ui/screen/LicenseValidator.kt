package com.nusantaraskd.ui.screen

import android.content.Context
import android.provider.Settings
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
class LicenseValidator @Inject constructor(
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
            
            // Simpan flag aktivasi
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
