package com.nusantaraskd.ui.screen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _navigateTo = MutableStateFlow<String?>(null)
    val navigateTo: StateFlow<String?> = _navigateTo.asStateFlow()

    init {
        checkActivation()
    }

    private fun checkActivation() {
        viewModelScope.launch {
            val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            val isActivated = prefs.getBoolean("is_activated", false)
            
            delay(1000)
            _navigateTo.value = if (isActivated) "home" else "activation"
        }
    }
}
