package com.nusantaraskd.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nusantaraskd.data.repository.QuestionRepository

@Composable
fun AppNavigation(questionRepository: QuestionRepository) {
    val navController = rememberNavController()

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
            ActivationScreen(onActivated = {
                navController.navigate("home") {
                    popUpTo("activation") { inclusive = true }
                }
            })
        }
        composable("home") {
            HomeScreen(
                onStartExam = { navController.navigate("pilih_paket") },
                onNavigateMenu = { route ->
                    if (route == "practice") {
                        navController.navigate("pilih_paket")
                    } else {
                        navController.navigate(route)
                    }
                }
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
                onStart = { navController.navigate("exam") { popUpTo("home") } },
                onBack = { navController.popBackStack() }
            )
        }
        composable("exam") {
            ExamScreen(
                onFinish = { navController.navigate("result") { popUpTo("home") } }
            )
        }
        composable("result") {
            ResultScreen(
                onHome = { navController.navigate("home") { popUpTo("home") { inclusive = true } } }
            )
        }
        composable("practice") { PlaceholderScreen("Mode Latihan") { navController.popBackStack() } }
        composable("weakness") { PlaceholderScreen("Bank Soal Salah") { navController.popBackStack() } }
        composable("stats") { PlaceholderScreen("Analisis Skor") { navController.popBackStack() } }
        composable("history") { PlaceholderScreen("Riwayat Ujian") { navController.popBackStack() } }
    }
}
