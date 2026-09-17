package com.nusantaraskd.data.local.dao

import androidx.room.*
import com.nusantaraskd.data.local.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ExamSessionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(session: ExamSessionEntity): Long

    @Update
    suspend fun update(session: ExamSessionEntity)

    @Delete
    suspend fun delete(session: ExamSessionEntity)

    @Query("SELECT * FROM exam_sessions ORDER BY startedAt DESC")
    fun getAllFlow(): Flow<List<ExamSessionEntity>>

    @Query("SELECT * FROM exam_sessions ORDER BY startedAt DESC")
    suspend fun getAll(): List<ExamSessionEntity>

    @Query("SELECT * FROM exam_sessions WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): ExamSessionEntity?

    @Query("SELECT * FROM exam_sessions ORDER BY startedAt DESC LIMIT 1")
    suspend fun getLatest(): ExamSessionEntity?

    @Query("DELETE FROM exam_sessions WHERE id = :id")
    suspend fun deleteById(id: Long)
}
