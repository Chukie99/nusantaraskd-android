package com.nusantaraskd.ui.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nusantaraskd.data.local.entity.ExamSessionEntity
import com.nusantaraskd.data.local.entity.QuestionEntity
import com.nusantaraskd.data.local.entity.UserAnswerEntity
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

    private val _timeRemaining = MutableStateFlow(6000)
    val timeRemaining: StateFlow<Int> = _timeRemaining

    private var sessionStartTime = System.currentTimeMillis()
    private var timerJob: Job? = null

    init {
        loadQuestions()
        startTimer()
    }

    private fun loadQuestions() {
        viewModelScope.launch {
            questionRepository.seedIfEmpty()
            _questions.value = questionRepository.getAllQuestions()
        }
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_timeRemaining.value > 0) {
                delay(1000)
                _timeRemaining.value--
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
        _markedQuestions.value = if (currentMarks.contains(current)) currentMarks - current else currentMarks + current
    }

    fun nextQuestion() {
        if (_currentIndex.value < _questions.value.size - 1) _currentIndex.value++
    }

    fun prevQuestion() {
        if (_currentIndex.value > 0) _currentIndex.value--
    }

    fun goToQuestion(index: Int) {
        if (index in 0 until _questions.value.size) _currentIndex.value = index
    }

    suspend fun calculateResults(): ExamResultSummary {
        val qList = _questions.value
        val ansMap = _answers.value
        var twkScore = 0
        var tiuScore = 0
        var tkpScore = 0

        val userAnswerEntities = mutableListOf<UserAnswerEntity>()

        qList.forEachIndexed { index, q ->
            val selected = ansMap[index] ?: ""
            var isCorrect = false

            when (q.category.uppercase()) {
                "TWK" -> {
                    if (selected.isNotEmpty() && selected.equals(q.correctAnswer, ignoreCase = true)) {
                        twkScore += 5
                        isCorrect = true
                    }
                }
                "TIU" -> {
                    if (selected.isNotEmpty() && selected.equals(q.correctAnswer, ignoreCase = true)) {
                        tiuScore += 5
                        isCorrect = true
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
                    isCorrect = weight >= 4
                }
            }

            userAnswerEntities.add(
                UserAnswerEntity(
                    sessionId = 0,
                    questionId = q.id,
                    selectedOption = selected,
                    isCorrect = isCorrect,
                    timeTaken = 0
                )
            )
        }

        val totalScore = twkScore + tiuScore + tkpScore
        val passedTwk = twkScore >= 65
        val passedTiu = tiuScore >= 80
        val passedTkp = tkpScore >= 166
        val passedAll = passedTwk && passedTiu && passedTkp

        val session = ExamSessionEntity(
            type = "SIMULASI_SKD",
            startedAt = sessionStartTime,
            endedAt = System.currentTimeMillis(),
            scoreTwk = twkScore,
            scoreTiu = tiuScore,
            scoreTkp = tkpScore,
            scoreTotal = totalScore,
            passed = passedAll
        )

        val sessionId = examRepository.saveSession(session, userAnswerEntities)
        Log.d("EXAM_SAVE", "Session saved, id=$sessionId, total=$totalScore")

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
