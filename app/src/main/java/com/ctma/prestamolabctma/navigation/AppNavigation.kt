package com.ctma.prestamolabctma.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ctma.prestamolabctma.ui.login.LoginScreen
import com.ctma.prestamolabctma.ui.home.HomeScreen
import com.ctma.prestamolabctma.viewmodel.LoginViewModel
import com.ctma.prestamolabctma.ui.catalogo.CatalogoScreen
import com.ctma.prestamolabctma.ui.equipo.EquiposScreen
import com.ctma.prestamolabctma.ui.misprestamos.MisPrestamosScreen
import com.ctma.prestamolabctma.ui.solicitud.SolicitudesScreen
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
            HomeScreen(
                onCatalogoClick = {
                    navController.navigate("catalogo")
                },
                onEquiposClick = {
                    navController.navigate("equipos")
                },
                onPrestamosClick = {
                    navController.navigate("prestamos")
                },
                onSolicitudesClick = {
                    navController.navigate("solicitudes")
                }
            )
        }
        composable("catalogo") {
            CatalogoScreen()
        }

        composable("equipos") {
            EquiposScreen()
        }

        composable("prestamos") {
            MisPrestamosScreen()
        }

        composable("solicitudes") {
            SolicitudesScreen()
        }
    }
}