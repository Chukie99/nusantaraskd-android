package com.nusantaraskd.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
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
                onFinish = { score, twk, tiu, tkp, passed ->
                    navController.navigate("result/$score/$twk/$tiu/$tkp/$passed") {
                        popUpTo("home")
                    }
                }
            )
        }
        composable("result/{score}/{twk}/{tiu}/{tkp}/{passed}") { backStackEntry ->
            val score = backStackEntry.arguments?.getString("score")?.toInt() ?: 0
            val twk = backStackEntry.arguments?.getString("twk")?.toInt() ?: 0
            val tiu = backStackEntry.arguments?.getString("tiu")?.toInt() ?: 0
            val tkp = backStackEntry.arguments?.getString("tkp")?.toInt() ?: 0
            val passed = backStackEntry.arguments?.getString("passed")?.toBoolean() ?: false
            ResultScreen(
                score = score, twk = twk, tiu = tiu, tkp = tkp, passed = passed,
                onHome = { navController.navigate("home") { popUpTo("home") { inclusive = true } } }
            )
        }
        composable("practice") { PlaceholderScreen("Mode Latihan") { navController.popBackStack() } }
        composable("weakness") { PlaceholderScreen("Bank Soal Salah") { navController.popBackStack() } }
        composable("stats") { PlaceholderScreen("Analisis Skor") { navController.popBackStack() } }
        composable("history") { PlaceholderScreen("Riwayat Ujian") { navController.popBackStack() } }
    }
}
