package com.ctma.prestamolabctma.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ctma.prestamolabctma.ui.login.LoginScreen
import com.ctma.prestamolabctma.ui.home.HomeScreen
import com.ctma.prestamolabctma.viewmodel.LoginViewModel

@Composable
fun AppNavigation(
    loginViewModel: LoginViewModel
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        composable("login") {
            LoginScreen(
                loginViewModel = loginViewModel,
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable("home") {
            HomeScreen()
        }
    }
}