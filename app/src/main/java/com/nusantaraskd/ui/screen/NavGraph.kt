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
                onStartExam = { navController.navigate("exam") },
                onNavigateMenu = { route -> navController.navigate(route) }
            )
        }
        composable("exam") { PlaceholderScreen("Simulasi CAT SKD") { navController.popBackStack() } }
        composable("practice") { PlaceholderScreen("Mode Latihan") { navController.popBackStack() } }
        composable("weakness") { PlaceholderScreen("Bank Soal Salah") { navController.popBackStack() } }
        composable("stats") { PlaceholderScreen("Analisis Skor") { navController.popBackStack() } }
        composable("history") { PlaceholderScreen("Riwayat Ujian") { navController.popBackStack() } }
    }
}
