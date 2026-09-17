package com.nusantaraskd.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen { navController.navigate("activation") }
        }
        composable("activation") { ActivationScreen { navController.navigate("home") } }
        composable("home") { HomeScreen() }
    }
}

@Composable
fun ActivationScreen(onComplete: () -> Unit) {
    Text("Activation Screen")
}

@Composable
fun HomeScreen() {
    Text("Home Screen")
}