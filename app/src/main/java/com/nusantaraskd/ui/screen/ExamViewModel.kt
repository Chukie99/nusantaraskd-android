package com.nusantaraskd.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nusantaraskd.data.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExamViewModel @Inject constructor(
    private val questionRepository: QuestionRepository
) : ViewModel() {
    private val _questions = MutableStateFlow<List<com.nusantaraskd.data.local.entity.QuestionEntity>>(emptyList())
    val questions: StateFlow<List<com.nusantaraskd.data.local.entity.QuestionEntity>> = _questions

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex

    private val _selectedOptions = MutableStateFlow<Map<Int, String>>(emptyMap())
    val selectedOptions: StateFlow<Map<Int, String>> = _selectedOptions

    init {
        loadQuestions()
    }

    private fun loadQuestions() {
        viewModelScope.launch {
            _questions.value = questionRepository.getAllQuestions()
        }
    }

    fun selectOption(questionIndex: Int, option: String) {
        _selectedOptions.value = _selectedOptions.value + (questionIndex to option)
    }

    fun setCurrentQuestion(index: Int) {
        if (index >= 0 && index < _questions.value.size) {
            _currentQuestionIndex.value = index
        }
    }

    fun getCurrentQuestion(): com.nusantaraskd.data.local.entity.QuestionEntity? {
        return _questions.value.getOrNull(_currentQuestionIndex.value)
    }
}
