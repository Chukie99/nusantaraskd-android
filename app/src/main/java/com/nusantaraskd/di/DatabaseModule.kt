package com.nusantaraskd.di

import android.content.Context
import androidx.room.Room
import com.nusantaraskd.data.local.AppDatabase
import com.nusantaraskd.data.local.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "nusantara_skd_database"
        ).build()
    }

    @Provides
    fun provideQuestionDao(database: AppDatabase): QuestionDao = database.questionDao()

    @Provides
    fun provideExamSessionDao(database: AppDatabase): ExamSessionDao = database.examSessionDao()

    @Provides
    fun provideUserAnswerDao(database: AppDatabase): UserAnswerDao = database.userAnswerDao()

    @Provides
    fun provideWrongQuestionDao(database: AppDatabase): WrongQuestionDao = database.wrongQuestionDao()

    @Provides
    fun provideAppStateDao(database: AppDatabase): AppStateDao = database.appStateDao()
}
