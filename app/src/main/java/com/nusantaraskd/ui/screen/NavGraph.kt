package com.nusantaraskd.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onStartExam = { navController.navigate("exam") },
                onNavigateMenu = { route -> navController.navigate(route) }
            )
        }
        composable("exam") {
            ExamScreen(
                onFinish = { _, _, _, _, _ ->
                    navController.navigate("home") {
                        popUpTo("exam") { inclusive = true }
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
            PlaceholderScreen(title = "Mode Latihan", onBack = { navController.popBackStack() })
        }
        composable("weakness") {
            PlaceholderScreen(title = "Bank Soal Salah", onBack = { navController.popBackStack() })
        }
    }
}
