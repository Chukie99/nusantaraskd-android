package com.nusantaraskd.data.local.dao

import androidx.room.*
import com.nusantaraskd.data.local.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(questions: List<QuestionEntity>)
    @Query("SELECT * FROM questions")
    suspend fun getAll(): List<QuestionEntity>
    @Query("SELECT * FROM questions WHERE category = :cat")
    suspend fun getByCategory(cat: String): List<QuestionEntity>
    @Query("SELECT * FROM questions WHERE subCategory = :sub")
    suspend fun getBySubCategory(sub: String): List<QuestionEntity>
    @Query("SELECT COUNT(*) FROM questions")
    suspend fun count(): Int
}

@Dao
interface ExamSessionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(session: ExamSessionEntity): Long
    @Query("SELECT * FROM exam_sessions ORDER BY startedAt DESC")
    fun getAllFlow(): Flow<List<ExamSessionEntity>>
    @Query("SELECT * FROM exam_sessions ORDER BY startedAt DESC")
    suspend fun getAll(): List<ExamSessionEntity>
    @Query("SELECT * FROM exam_sessions WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): ExamSessionEntity?
    @Query("SELECT * FROM exam_sessions ORDER BY startedAt DESC LIMIT 1")
    suspend fun getLatest(): ExamSessionEntity?
}

@Dao
interface UserAnswerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(answers: List<UserAnswerEntity>)
}

@Dao
interface WrongQuestionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(wrong: WrongQuestionEntity)
}

@Dao
interface AppStateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun set(state: AppStateEntity)
    @Query("SELECT * FROM app_state WHERE key = :key LIMIT 1")
    suspend fun get(key: String): AppStateEntity?
}
