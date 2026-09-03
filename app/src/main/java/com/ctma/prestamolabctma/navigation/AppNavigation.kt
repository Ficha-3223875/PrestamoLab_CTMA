package com.ctma.prestamolabctma.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.ctma.prestamolabctma.model.Equipo
import com.ctma.prestamolabctma.ui.catalogo.CatalogoScreen
import com.ctma.prestamolabctma.ui.equipo.DetalleEquipoScreen
import com.ctma.prestamolabctma.ui.equipo.EquiposScreen
import com.ctma.prestamolabctma.ui.home.HomeScreen
import com.ctma.prestamolabctma.ui.login.LoginScreen
import com.ctma.prestamolabctma.ui.misprestamos.MisPrestamosScreen
import com.ctma.prestamolabctma.ui.solicitud.SolicitudScreen
import com.ctma.prestamolabctma.ui.solicitud.SolicitudesScreen
import com.ctma.prestamolabctma.viewmodel.LoginViewModel
import com.ctma.prestamolabctma.viewmodel.SolicitudViewModel


@Composable
fun AppNavigation(
    loginViewModel: LoginViewModel
) {

    // Controlador de navegación
    val navController = rememberNavController()

    // ViewModel de solicitudes
    val solicitudViewModel: SolicitudViewModel = viewModel()

    // Lista de solicitudes
    val solicitudes by solicitudViewModel.solicitudes
        .collectAsStateWithLifecycle()

    // Equipo seleccionado en el catálogo
    var equipoSeleccionado by remember {
        mutableStateOf<Equipo?>(null)
    }


    NavHost(
        navController = navController,
        startDestination = "login"
    ) {


        // =====================================================
        // LOGIN
        // =====================================================

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


        // =====================================================
        // HOME
        // =====================================================

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


        // =====================================================
        // CATÁLOGO
        // =====================================================

        composable("catalogo") {

            CatalogoScreen(

                onEquipoClick = { equipo ->

                    // Guardamos el equipo seleccionado
                    equipoSeleccionado = equipo

                    // Vamos al detalle
                    navController.navigate("detalle_equipo")
                }
            )
        }


        // =====================================================
        // DETALLE DEL EQUIPO
        // =====================================================

        composable("detalle_equipo") {

            equipoSeleccionado?.let { equipo ->

                DetalleEquipoScreen(

                    equipo = equipo,

                    onSolicitarClick = {

                        // Ir al formulario de solicitud
                        navController.navigate("nueva_solicitud")
                    }
                )
            }
        }


        // =====================================================
        // NUEVA SOLICITUD
        // =====================================================

        composable("nueva_solicitud") {

            equipoSeleccionado?.let { equipo ->

                SolicitudScreen(

                    equipo = equipo,

                    onSolicitudEnviada = { solicitud ->

                        // Guardamos la solicitud en el ViewModel
                        solicitudViewModel.agregarSolicitud(solicitud)

                        // Regresamos a la pantalla de solicitudes
                        navController.navigate("solicitudes") {

                            popUpTo("nueva_solicitud") {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }


        // =====================================================
        // EQUIPOS
        // =====================================================

        composable("equipos") {

            EquiposScreen()
        }


// MIS PRÉSTAMOS
        composable("prestamos") {

            MisPrestamosScreen(
                solicitudes = solicitudes
            )
        }

        // =====================================================
        // SOLICITUDES
        // =====================================================
        composable("solicitudes") {

            SolicitudesScreen(
                solicitudes = solicitudes,

                onAprobarClick = { solicitud ->

                    solicitudViewModel.actualizarEstado(
                        idSolicitud = solicitud.id,
                        nuevoEstado = "Aprobada"
                    )
                },

                onRechazarClick = { solicitud ->

                    solicitudViewModel.actualizarEstado(
                        idSolicitud = solicitud.id,
                        nuevoEstado = "Rechazada"
                    )
                }
            )
        }
    }
}