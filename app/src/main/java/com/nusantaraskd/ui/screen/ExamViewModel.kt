package com.nusantaraskd.ui.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nusantaraskd.data.local.entity.QuestionEntity
import com.nusantaraskd.data.repository.ExamRepository
import com.nusantaraskd.data.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExamViewModel @Inject constructor(
    private val questionRepository: QuestionRepository,
    private val examRepository: ExamRepository
) : ViewModel() {

    private val _questions = MutableStateFlow<List<QuestionEntity>>(emptyList())
    val questions: StateFlow<List<QuestionEntity>> = _questions

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex

    private val _answers = MutableStateFlow<Map<Int, String>>(emptyMap())
    val answers: StateFlow<Map<Int, String>> = _answers

    private val _markedQuestions = MutableStateFlow<Set<Int>>(emptySet())
    val markedQuestions: StateFlow<Set<Int>> = _markedQuestions

    private val _timeRemaining = MutableStateFlow(6000) // 100 minutes in seconds
    val timeRemaining: StateFlow<Int> = _timeRemaining

    private var timerJob: Job? = null

    init {
        loadQuestions()
        startTimer()
    }

    private fun loadQuestions() {
        viewModelScope.launch {
            questionRepository.seedIfEmpty()
            _questions.value = questionRepository.getAllQuestions()
            Log.d("EXAM_VM", "Loaded ${_questions.value.size} questions")
        }
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_timeRemaining.value > 0) {
                delay(1000)
                _timeRemaining.value--
                if (_timeRemaining.value % 30 == 0) {
                    Log.d("EXAM_TIMER", "Time remaining: ${_timeRemaining.value}s")
                }
            }
        }
    }

    fun selectOption(option: String) {
        val current = _currentIndex.value
        _answers.value = _answers.value + (current to option)
    }

    fun toggleMark() {
        val current = _currentIndex.value
        val currentMarks = _markedQuestions.value
        _markedQuestions.value = if (currentMarks.contains(current)) {
            currentMarks - current
        } else {
            currentMarks + current
        }
    }

    fun nextQuestion() {
        if (_currentIndex.value < _questions.value.size - 1) {
            _currentIndex.value++
        }
    }

    fun prevQuestion() {
        if (_currentIndex.value > 0) {
            _currentIndex.value--
        }
    }

    fun goToQuestion(index: Int) {
        if (index in 0 until _questions.value.size) {
            _currentIndex.value = index
        }
    }

    fun calculateResults(): ExamResultSummary {
        val qList = _questions.value
        val ansMap = _answers.value

        var twkScore = 0
        var tiuScore = 0
        var tkpScore = 0

        qList.forEachIndexed { index, q ->
            val selected = ansMap[index] ?: ""
            when (q.category.uppercase()) {
                "TWK" -> {
                    if (selected.isNotEmpty() && selected.equals(q.correctAnswer, ignoreCase = true)) {
                        twkScore += 5
                    }
                }
                "TIU" -> {
                    if (selected.isNotEmpty() && selected.equals(q.correctAnswer, ignoreCase = true)) {
                        tiuScore += 5
                    }
                }
                "TKP" -> {
                    val weight = when (selected.uppercase()) {
                        "A" -> q.weightA
                        "B" -> q.weightB
                        "C" -> q.weightC
                        "D" -> q.weightD
                        "E" -> q.weightE
                        else -> 0
                    }
                    tkpScore += weight
                }
            }
        }

        val totalScore = twkScore + tiuScore + tkpScore
        val passedTwk = twkScore >= 65
        val passedTiu = tiuScore >= 80
        val passedTkp = tkpScore >= 166
        val passedAll = passedTwk && passedTiu && passedTkp

        return ExamResultSummary(totalScore, twkScore, tiuScore, tkpScore, passedAll)
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}

data class ExamResultSummary(
    val totalScore: Int,
    val twkScore: Int,
    val tiuScore: Int,
    val tkpScore: Int,
    val passed: Boolean
)
