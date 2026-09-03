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
import com.ctma.prestamolabctma.viewmodel.EquipoViewModel
import com.ctma.prestamolabctma.viewmodel.LoginViewModel
import com.ctma.prestamolabctma.viewmodel.SolicitudViewModel


@Composable
fun AppNavigation(
    loginViewModel: LoginViewModel
) {

    val navController = rememberNavController()

    // ViewModel de solicitudes
    val solicitudViewModel: SolicitudViewModel = viewModel()

    // ViewModel de equipos
    val equipoViewModel: EquipoViewModel = viewModel()

    // Lista de solicitudes
    val solicitudes by solicitudViewModel
        .solicitudes
        .collectAsStateWithLifecycle()

    // Lista de equipos
    val equipos by equipoViewModel
        .equipos
        .collectAsStateWithLifecycle()

    // Equipo seleccionado
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
                equipos = equipos,

                onEquipoClick = { equipo ->

                    equipoSeleccionado = equipo

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

                        solicitudViewModel.agregarSolicitud(
                            solicitud
                        )

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

            EquiposScreen(
                equipos = equipos,
                onVolverClick = {
                    navController.popBackStack()
                }
            )
        }


        // =====================================================
        // MIS PRÉSTAMOS
        // =====================================================

        composable("prestamos") {

            MisPrestamosScreen(
                solicitudes = solicitudes,

                onVolverClick = {
                    navController.popBackStack()
                },

                onDevolverClick = { solicitud ->

                    solicitudViewModel.devolverPrestamo(
                        solicitud.id
                    )

                    equipoViewModel.actualizarDisponibilidad(
                        idEquipo = solicitud.equipo.id,
                        disponible = true
                    )
                }
            )
        }


        // =====================================================
        // SOLICITUDES
        // =====================================================

        composable("solicitudes") {

            SolicitudesScreen(
                solicitudes = solicitudes,

                onVolverClick = {
                    navController.popBackStack()
                },

                onCambiarEstado = { solicitudId, nuevoEstado ->

                    solicitudViewModel.cambiarEstado(
                        solicitudId = solicitudId,
                        nuevoEstado = nuevoEstado
                    )

                    if (nuevoEstado.equals("Aprobada", ignoreCase = true)) {

                        val solicitud = solicitudes.find {
                            it.id == solicitudId
                        }

                        solicitud?.let {

                            equipoViewModel.actualizarDisponibilidad(
                                idEquipo = it.equipo.id,
                                disponible = false
                            )
                        }
                    }
                }
            )
        }
    }
}