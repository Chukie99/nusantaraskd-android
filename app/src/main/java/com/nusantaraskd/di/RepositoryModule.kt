package com.nusantaraskd.di

import com.nusantaraskd.data.local.dao.*
import com.nusantaraskd.data.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideQuestionRepository(questionDao: QuestionDao): QuestionRepository = QuestionRepositoryImpl(questionDao)

    @Provides
    @Singleton
    fun provideExamRepository(examSessionDao: ExamSessionDao, userAnswerDao: UserAnswerDao): ExamRepository = ExamRepositoryImpl(examSessionDao, userAnswerDao)

    @Provides
    @Singleton
    fun provideAppStateRepository(appStateDao: AppStateDao): AppStateRepository = AppStateRepositoryImpl(appStateDao)
}
