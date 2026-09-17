package com.nusantaraskd.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Database
import androidx.room.RoomDatabase

@Entity(tableName = "questions")
data class QuestionEntity(
    @PrimaryKey val id: String,
    val category: String,
    val questionText: String
)

@Entity(tableName = "exam_sessions")
data class ExamSessionEntity(
    @PrimaryKey val id: String,
    val score: Int
)

@Entity(tableName = "app_state")
data class AppStateEntity(
    @PrimaryKey val key: String,
    val value: String
)

@Dao
interface QuestionDao {
    @Query("SELECT * FROM questions")
    suspend fun getAll(): List<QuestionEntity>
}

@Database(entities = [QuestionEntity::class, ExamSessionEntity::class, AppStateEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
}