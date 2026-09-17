package com.nusantaraskd.data.repository

import com.nusantaraskd.data.local.dao.*
import com.nusantaraskd.data.local.entity.*
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
            val dummyQuestions = listOf(
                QuestionEntity(category = "TWK", subCategory = "Pancasila", questionText = "Pancasila sebagai dasar negara secara resmi disahkan oleh...", optionA = "BPUPKI", optionB = "PPKI", optionC = "Panitia Sembilan", optionD = "DPR", optionE = "MPR", correctAnswer = "B", weight = 5, explanation = "PPKI mengesahkan UUD 1945 dan Pancasila sebagai dasar negara pada 18 Agustus 1945."),
                QuestionEntity(category = "TWK", subCategory = "UUD 1945", questionText = "Pasal 1 ayat (1) UUD 1945 menyatakan bahwa Negara Indonesia adalah negara...", optionA = "Hukum", optionB = "Demokrasi", optionC = "Kesatuan", optionD = "Republik", optionE = "Pancasila", correctAnswer = "C", weight = 5, explanation = "Berdasarkan Pasal 1 ayat (1), Negara Indonesia adalah negara kesatuan yang berbentuk republik."),
                QuestionEntity(category = "TWK", subCategory = "Sejarah", questionText = "Sumpah Pemuda dibacakan pada tanggal...", optionA = "20 Mei 1908", optionB = "28 Oktober 1928", optionC = "17 Agustus 1945", optionD = "1 Juni 1945", optionE = "10 November 1945", correctAnswer = "B", weight = 5, explanation = "Kongres Pemuda II melahirkan keputusan Sumpah Pemuda pada 28 Oktober 1928."),
                QuestionEntity(category = "TIU", subCategory = "Verbal", questionText = "Sinonim kata EKLEKTIK adalah...", optionA = "Campuran", optionB = "Murni", optionC = "Tetap", optionD = "Tunggal", optionE = "Satu", correctAnswer = "A", weight = 5, explanation = "Eklektik berarti memilih yang terbaik dari berbagai sumber."),
                QuestionEntity(category = "TKP", subCategory = "Pelayanan Publik", questionText = "Anda adalah seorang pegawai loket pelayanan. Seorang tamu datang dengan marah-marah karena antrean terlalu panjang. Sikap Anda...", optionA = "Memarahi balik tamu tersebut", optionB = "Mendengarkan dengan sabar dan memberikan solusi terbaik", optionC = "Mengabaikannya sampai ia tenang", optionD = "Menyuruhnya pindah ke loket lain", optionE = "Melaporkannya kepada satpam", correctAnswer = "B", weight = 5, explanation = "Sebagai pelayan publik, kita harus sabar, empati, dan profesional dalam memberikan solusi.")
            )
            questionDao.insertAll(dummyQuestions)
        }
    }
}

interface ExamRepository {
    suspend fun saveSession(session: ExamSessionEntity, answers: List<UserAnswerEntity>)
    suspend fun getAllSessions(): List<ExamSessionEntity>
    suspend fun getLatestSession(): ExamSessionEntity?
}

@Singleton
class ExamRepositoryImpl @Inject constructor(
    private val examSessionDao: ExamSessionDao,
    private val userAnswerDao: UserAnswerDao
) : ExamRepository {
    override suspend fun saveSession(session: ExamSessionEntity, answers: List<UserAnswerEntity>) {
        examSessionDao.insert(session)
        userAnswerDao.insertAll(answers)
    }

    override suspend fun getAllSessions(): List<ExamSessionEntity> = examSessionDao.getAll()

    override suspend fun getLatestSession(): ExamSessionEntity? = examSessionDao.getLatest()
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
