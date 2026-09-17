package com.nusantaraskd.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nusantaraskd.data.local.entity.ExamSessionEntity
import com.nusantaraskd.data.repository.ExamRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val examRepository: ExamRepository
) : ViewModel() {

    private val _sessions = MutableStateFlow<List<ExamSessionEntity>>(emptyList())
    val sessions: StateFlow<List<ExamSessionEntity>> = _sessions

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        loadSessions()
    }

    private fun loadSessions() {
        viewModelScope.launch {
            _isLoading.value = true
            _sessions.value = examRepository.getAllSessions()
            _isLoading.value = false
        }
    }
}
