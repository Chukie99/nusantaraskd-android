package com.nusantaraskd.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Database
import androidx.room.RoomDatabase

@Entity(tableName = "questions")
data class QuestionEntity(
    @PrimaryKey val id: String,
    val category: String, // "TWK", "TIU", "TKP"
    val questionText: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val optionE: String,
    val correctAnswer: String, // "A", "B", "C", "D", "E" (untuk TWK/TIU)
    val weightA: Int = 0, // Untuk TKP (skala 1-5)
    val weightB: Int = 0,
    val weightC: Int = 0,
    val weightD: Int = 0,
    val weightE: Int = 0,
    val explanation: String
)

@Entity(tableName = "exam_sessions")
data class ExamSessionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: Long,
    val scoreTwk: Int,
    val scoreTiu: Int,
    val scoreTkp: Int,
    val totalScore: Int,
    val isPassed: Boolean, // TWK >= 65, TIU >= 80, TKP >= 166
    val durationSeconds: Int
)

@Dao
interface QuestionDao {
    @Query("SELECT * FROM questions")
    suspend fun getAll(): List<QuestionEntity>

    @Query("SELECT * FROM questions WHERE category = :cat ORDER BY RANDOM() LIMIT :limit")
    suspend fun getRandomByCategory(cat: String, limit: Int): List<QuestionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(questions: List<QuestionEntity>)

    @Query("SELECT COUNT(*) FROM questions")
    suspend fun getCount(): Int
}

@Dao
interface ExamDao {
    @Insert
    suspend fun insertSession(session: ExamSessionEntity): Long

    @Query("SELECT * FROM exam_sessions ORDER BY date DESC")
    suspend fun getAllSessions(): List<ExamSessionEntity>
}

@Database(entities = [QuestionEntity::class, ExamSessionEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
    abstract fun examDao(): ExamDao
}
