package com.nusantaraskd.data.repository

import com.nusantaraskd.data.local.entity.ExamSessionEntity
import com.nusantaraskd.data.local.entity.UserAnswerEntity
import kotlinx.coroutines.flow.Flow

interface ExamRepository {
    suspend fun saveSession(session: ExamSessionEntity, answers: List<UserAnswerEntity>): Long
    fun getAllSessionsFlow(): Flow<List<ExamSessionEntity>>
    suspend fun getAllSessions(): List<ExamSessionEntity>
    suspend fun getLatestSession(): ExamSessionEntity?
    suspend fun getSessionById(id: Long): ExamSessionEntity?
}
