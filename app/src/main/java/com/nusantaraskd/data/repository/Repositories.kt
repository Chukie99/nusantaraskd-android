package com.nusantaraskd.data.repository

import com.nusantaraskd.data.local.dao.*
import com.nusantaraskd.data.local.entity.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

interface QuestionRepository {
    suspend fun getAllQuestions(): List<QuestionEntity>
    suspend fun getQuestionsByCategory(category: String): List<QuestionEntity>
    suspend fun getQuestionsBySubCategory(subCategory: String): List<QuestionEntity>
    suspend fun getQuestionCount(): Int
    suspend fun seedIfEmpty()
}

@Singleton
class QuestionRepositoryImpl @Inject constructor(
    private val questionDao: QuestionDao
) : QuestionRepository {
    override suspend fun getAllQuestions(): List<QuestionEntity> = questionDao.getAll()
    override suspend fun getQuestionsByCategory(category: String): List<QuestionEntity> = questionDao.getByCategory(category)
    override suspend fun getQuestionsBySubCategory(subCategory: String): List<QuestionEntity> = questionDao.getBySubCategory(subCategory)
    override suspend fun getQuestionCount(): Int = questionDao.count()

    override suspend fun seedIfEmpty() {
        if (questionDao.count() == 0) {
            val jsonString = try {
                val inputStream = javax.inject.Inject::class.java.classLoader!!.getResourceAsStream("questions_bank_1100.json")
                    ?: javax.inject.Inject::class.java.classLoader!!.getResourceAsStream("questions.json")
                inputStream?.bufferedReader().use { it?.readText() }
            } catch (e: Exception) { null }
            
            if (jsonString != null) {
                val list = kotlinx.serialization.json.Json.decodeFromString<List<QuestionEntity>>(jsonString)
                questionDao.insertAll(list)
            } else {
                // Fallback dummy
                questionDao.insertAll(listOf(QuestionEntity(category = "TWK", subCategory = "Sejarah", questionText = "Dummy soal", optionA = "A", optionB = "B", optionC = "C", optionD = "D", optionE = "E", weightA = 5, weightB = 0, weightC = 0, weightD = 0, weightE = 0, correctAnswer = "A", explanation = "B")))
            }
        }
    }
}

@Singleton
class ExamRepositoryImpl @Inject constructor(
    private val examSessionDao: ExamSessionDao,
    private val userAnswerDao: UserAnswerDao
) : ExamRepository {
    override suspend fun saveSession(session: ExamSessionEntity, answers: List<UserAnswerEntity>): Long {
        val sessionId = examSessionDao.insert(session)
        val updatedAnswers = answers.map { it.copy(sessionId = sessionId) }
        userAnswerDao.insertAll(updatedAnswers)
        return sessionId
    }
    override fun getAllSessionsFlow(): Flow<List<ExamSessionEntity>> = examSessionDao.getAllFlow()
    override suspend fun getAllSessions(): List<ExamSessionEntity> = examSessionDao.getAll()
    override suspend fun getLatestSession(): ExamSessionEntity? = examSessionDao.getLatest()
    override suspend fun getSessionById(id: Long): ExamSessionEntity? = examSessionDao.getById(id)
}

interface AppStateRepository {
    suspend fun get(key: String): String?
    suspend fun set(key: String, value: String)
}

@Singleton
class AppStateRepositoryImpl @Inject constructor(
    private val appStateDao: AppStateDao
) : AppStateRepository {
    override suspend fun get(key: String): String? = appStateDao.get(key)?.value
    override suspend fun set(key: String, value: String) {
        appStateDao.set(AppStateEntity(key, value))
    }
}
