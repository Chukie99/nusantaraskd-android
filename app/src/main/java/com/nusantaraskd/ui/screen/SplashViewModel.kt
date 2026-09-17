package com.nusantaraskd.ui.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nusantaraskd.data.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val questionRepository: QuestionRepository
) : ViewModel() {

    fun initializeDatabase() {
        viewModelScope.launch {
            questionRepository.seedIfEmpty()
            val count = questionRepository.getQuestionCount()
            Log.d("DB_INIT", "Total soal di database: $count")
        }
    }
}
