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

data class StatsSummary(
    val totalUjian: Int = 0,
    val avgTwk: Int = 0,
    val avgTiu: Int = 0,
    val avgTkp: Int = 0,
    val avgTotal: Int = 0,
    val maxTotal: Int = 0,
    val minTotal: Int = 0,
    val totalLolos: Int = 0,
    val totalBelum: Int = 0
)

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val examRepository: ExamRepository
) : ViewModel() {

    private val _stats = MutableStateFlow(StatsSummary())
    val stats: StateFlow<StatsSummary> = _stats

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        loadStats()
    }

    private fun loadStats() {
        viewModelScope.launch {
            _isLoading.value = true
            val sessions = examRepository.getAllSessions()
            _stats.value = calculateStats(sessions)
            _isLoading.value = false
        }
    }

    private fun calculateStats(sessions: List<ExamSessionEntity>): StatsSummary {
        if (sessions.isEmpty()) return StatsSummary()
        val total = sessions.size
        val sumTwk = sessions.sumOf { it.scoreTwk }
        val sumTiu = sessions.sumOf { it.scoreTiu }
        val sumTkp = sessions.sumOf { it.scoreTkp }
        val sumTotal = sessions.sumOf { it.scoreTotal }
        return StatsSummary(
            totalUjian = total,
            avgTwk = sumTwk / total,
            avgTiu = sumTiu / total,
            avgTkp = sumTkp / total,
            avgTotal = sumTotal / total,
            maxTotal = sessions.maxOf { it.scoreTotal },
            minTotal = sessions.minOf { it.scoreTotal },
            totalLolos = sessions.count { it.passed },
            totalBelum = sessions.count { !it.passed }
        )
    }
}
