package com.nusantaraskd.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nusantaraskd.data.room.QuestionEntity

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    // Sample / Dummy Questions (bisa diisi dari seeder/database)
    val dummyQuestions = remember {
        listOf(
            QuestionEntity("1", "TWK", "Pancasila sebagai dasar negara Indonesia pertama kali dirumuskan dalam sidang...", "BPUPKI I", "BPUPKI II", "PPKI I", "PPKI II", "Proklamasi", "A", explanation = "Dirumuskan pada sidang pertama BPUPKI (29 Mei - 1 Juni 1945)."),
            QuestionEntity("2", "TIU", "Jika A = 5 dan B = 3, maka A^2 - B^2 = ...", "16", "14", "12", "8", "4", "A", explanation = "A^2 - B^2 = 25 - 9 = 16.")
        )
    }

    NavHost(navController = navController, startDestination = "splash") {

        composable("splash") {
            SplashScreen(
                onNavigateNext = {
                    navController.navigate("activation") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }

        composable("activation") {
            ActivationScreen(
                onActivated = {
                    navController.navigate("home") {
                        popUpTo("activation") { inclusive = true }
                    }
                }
            )
        }

        composable("home") {
            HomeScreen(
                onStartExam = { navController.navigate("pilih_paket") },
                onNavigateMenu = { route -> navController.navigate(route) }
            )
        }

        composable("pilih_paket") {
            PilihPaketScreen(
                onBack = { navController.popBackStack() },
                onSelect = { _ -> navController.navigate("exam_instruction") }
            )
        }

        composable("exam_instruction") {
            ExamInstructionScreen(
                onStart = {
                    navController.navigate("exam") {
                        popUpTo("home")
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable("exam") {
            ExamScreen(
                questions = dummyQuestions,
                onFinishExam = { scoreTwk, scoreTiu, scoreTkp, userAnswers ->
                    navController.navigate("summary/$scoreTwk/$scoreTiu/$scoreTkp") {
                        popUpTo("home")
                    }
                }
            )
        }

        composable(
            "summary/{twk}/{tiu}/{tkp}",
            arguments = listOf(
                navArgument("twk") { type = NavType.IntType },
                navArgument("tiu") { type = NavType.IntType },
                navArgument("tkp") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val twk = backStackEntry.arguments?.getInt("twk") ?: 0
            val tiu = backStackEntry.arguments?.getInt("tiu") ?: 0
            val tkp = backStackEntry.arguments?.getInt("tkp") ?: 0

            ExamSummaryScreen(
                scoreTwk = twk,
                scoreTiu = tiu,
                scoreTkp = tkp,
                questions = dummyQuestions,
                userAnswers = emptyMap(),
                onBackHome = {
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )
        }
        
        composable("history") {
            RiwayatScreen(onBack = { navController.popBackStack() })
        }

        composable("stats") {
            StatistikScreen(onBack = { navController.popBackStack() })
        }

        composable("practice") {
            PlaceholderScreen(
                title = "Mode Latihan",
                onBack = { navController.popBackStack() }
            )
        }

        composable("weakness") {
            PlaceholderScreen(
                title = "Bank Soal Salah",
                onBack = { navController.popBackStack() }
            )
        }
    }
}
