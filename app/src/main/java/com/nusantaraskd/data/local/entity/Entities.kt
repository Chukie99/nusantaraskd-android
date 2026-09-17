package com.nusantaraskd.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class QuestionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val category: String, // TWK, TIU, TKP
    val subCategory: String,
    val questionText: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val optionE: String,
    // Untuk TWK/TIU: cuma satu yang 5, lainnya 0
    // Untuk TKP: semua opsi punya bobot 1-5 (variasi)
    val weightA: Int = 0,
    val weightB: Int = 0,
    val weightC: Int = 0,
    val weightD: Int = 0,
    val weightE: Int = 0,
    val correctAnswer: String = "", // Kosong untuk TKP
    val explanation: String
)

@Entity(tableName = "exam_sessions")
data class ExamSessionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val type: String, // TRYOUT, LATIHAN
    val startedAt: Long,
    val endedAt: Long,
    val scoreTwk: Int = 0,
    val scoreTiu: Int = 0,
    val scoreTkp: Int = 0,
    val scoreTotal: Int = 0,
    val passed: Boolean = false
)

@Entity(tableName = "user_answers")
data class UserAnswerEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sessionId: Long,
    val questionId: Long,
    val selectedOption: String,
    val isCorrect: Boolean,
    val timeTaken: Int = 0
)

@Entity(tableName = "wrong_questions")
data class WrongQuestionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val questionId: Long,
    val wrongCount: Int = 1,
    val lastWrongAt: Long
)

@Entity(tableName = "app_state")
data class AppStateEntity(
    @PrimaryKey val key: String,
    val value: String
)
