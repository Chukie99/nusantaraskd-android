package com.nusantaraskd.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nusantaraskd.data.local.dao.*
import com.nusantaraskd.data.local.entity.*

@Database(
    entities = [QuestionEntity::class, ExamSessionEntity::class, UserAnswerEntity::class, WrongQuestionEntity::class, AppStateEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
    abstract fun examSessionDao(): ExamSessionDao
    abstract fun userAnswerDao(): UserAnswerDao
    abstract fun wrongQuestionDao(): WrongQuestionDao
    abstract fun appStateDao(): AppStateDao
}
