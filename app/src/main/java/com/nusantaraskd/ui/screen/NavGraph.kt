package com.nusantaraskd.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AppNavGraph() {
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
                onFinish = { score, twk, tiu, tkp, passed ->
                    navController.navigate("result/$score/$twk/$tiu/$tkp/$passed") {
                        popUpTo("home")
                    }
                }
            )
        }

        composable(
            "result/{score}/{twk}/{tiu}/{tkp}/{passed}",
            arguments = listOf(
                navArgument("score") { type = NavType.IntType },
                navArgument("twk") { type = NavType.IntType },
                navArgument("tiu") { type = NavType.IntType },
                navArgument("tkp") { type = NavType.IntType },
                navArgument("passed") { type = NavType.BoolType }
            )
        ) { backStackEntry ->
            val score = backStackEntry.arguments?.getInt("score") ?: 0
            val twk = backStackEntry.arguments?.getInt("twk") ?: 0
            val tiu = backStackEntry.arguments?.getInt("tiu") ?: 0
            val tkp = backStackEntry.arguments?.getInt("tkp") ?: 0
            val passed = backStackEntry.arguments?.getBoolean("passed") ?: false

            ResultScreen(
                score = score,
                twk = twk,
                tiu = tiu,
                tkp = tkp,
                passed = passed,
                onHome = {
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
