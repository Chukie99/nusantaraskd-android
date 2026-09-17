package com.nusantaraskd.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.*
import androidx.compose.runtime.*

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onNavigate = { route -> navController.navigate(route) },
                onExit = { /* handle exit */ }
            )
        }
        composable("exam") {
            ExamScreen(
                onFinish = { total, twk, tiu, tkp, passed ->
                    // After exam, return to home
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
