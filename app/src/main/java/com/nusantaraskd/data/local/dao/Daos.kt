package com.nusantaraskd.data.local.dao

import androidx.room.*
import com.nusantaraskd.data.local.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(questions: List<QuestionEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(question: QuestionEntity)

    @Update
    suspend fun update(question: QuestionEntity)

    @Delete
    suspend fun delete(question: QuestionEntity)

    @Query("SELECT * FROM questions")
    suspend fun getAll(): List<QuestionEntity>

    @Query("SELECT * FROM questions WHERE category = :category")
    suspend fun getByCategory(category: String): List<QuestionEntity>

    @Query("SELECT * FROM questions WHERE subCategory = :subCategory")
    suspend fun getBySubCategory(subCategory: String): List<QuestionEntity>

    @Query("SELECT COUNT(*) FROM questions")
    suspend fun count(): Int
}

@Dao
interface ExamSessionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(session: ExamSessionEntity)

    @Update
    suspend fun update(session: ExamSessionEntity)

    @Delete
    suspend fun delete(session: ExamSessionEntity)

    @Query("SELECT * FROM exam_sessions ORDER BY startedAt DESC")
    suspend fun getAll(): List<ExamSessionEntity>

    @Query("SELECT * FROM exam_sessions WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): ExamSessionEntity?

    @Query("SELECT * FROM exam_sessions ORDER BY startedAt DESC LIMIT 1")
    suspend fun getLatest(): ExamSessionEntity?

    @Query("DELETE FROM exam_sessions WHERE id = :id")
    suspend fun deleteById(id: Long)
}

@Dao
interface UserAnswerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(answer: UserAnswerEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(answers: List<UserAnswerEntity>)

    @Query("SELECT * FROM user_answers WHERE sessionId = :sessionId")
    suspend fun getBySession(sessionId: Long): List<UserAnswerEntity>

    @Query("DELETE FROM user_answers WHERE sessionId = :sessionId")
    suspend fun deleteBySession(sessionId: Long)
}

@Dao
interface WrongQuestionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(question: WrongQuestionEntity)

    @Update
    suspend fun update(question: WrongQuestionEntity)

    @Delete
    suspend fun delete(question: WrongQuestionEntity)

    @Query("SELECT * FROM wrong_questions")
    suspend fun getAll(): List<WrongQuestionEntity>

    @Query("SELECT * FROM wrong_questions WHERE questionId = :questionId LIMIT 1")
    suspend fun getByQuestionId(questionId: Long): WrongQuestionEntity?

    @Query("DELETE FROM wrong_questions WHERE questionId = :questionId")
    suspend fun deleteByQuestionId(questionId: Long)
}

@Dao
interface AppStateDao {
    @Query("SELECT * FROM app_state WHERE key = :key LIMIT 1")
    suspend fun get(key: String): AppStateEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun set(entity: AppStateEntity)

    @Update
    suspend fun update(entity: AppStateEntity)

    @Delete
    suspend fun delete(entity: AppStateEntity)

    @Query("DELETE FROM app_state WHERE key = :key")
    suspend fun delete(key: String)
}
