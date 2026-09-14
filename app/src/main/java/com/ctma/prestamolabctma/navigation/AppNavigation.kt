package com.ctma.prestamolabctma.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.ctma.prestamolabctma.data.api.RetrofitInstance
import com.ctma.prestamolabctma.data.repository.UsuarioRepository
import com.ctma.prestamolabctma.data.session.SessionManager
import com.ctma.prestamolabctma.model.Equipo
import com.ctma.prestamolabctma.notification.NotificationHelper

import com.ctma.prestamolabctma.ui.catalogo.CatalogoScreen
import com.ctma.prestamolabctma.ui.equipo.DetalleEquipoScreen
import com.ctma.prestamolabctma.ui.equipo.EquiposScreen
import com.ctma.prestamolabctma.ui.home.HomeScreen
import com.ctma.prestamolabctma.ui.laboratorio.LaboratoriosScreen
import com.ctma.prestamolabctma.ui.login.LoginScreen
import com.ctma.prestamolabctma.ui.misprestamos.MisPrestamosScreen
import com.ctma.prestamolabctma.ui.registro.RegistroScreen
import com.ctma.prestamolabctma.ui.solicitud.SolicitudScreen
import com.ctma.prestamolabctma.ui.solicitud.SolicitudesScreen

import com.ctma.prestamolabctma.viewmodel.EquipoViewModel
import com.ctma.prestamolabctma.viewmodel.LaboratorioViewModel
import com.ctma.prestamolabctma.viewmodel.LoginViewModel
import com.ctma.prestamolabctma.viewmodel.RegistroViewModel
import com.ctma.prestamolabctma.viewmodel.RegistroViewModelFactory
import com.ctma.prestamolabctma.viewmodel.SolicitudViewModel
import com.ctma.prestamolabctma.viewmodel.SolicitudViewModelFactory

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AppNavigation(
    loginViewModel: LoginViewModel,
    sesionActiva: Boolean
) {

    val navController = rememberNavController()

    val context = LocalContext.current

    // =====================================================
    // SESIÓN
    // =====================================================

    val sessionManager = remember {
        SessionManager(context)
    }

    // =====================================================
    // VIEWMODEL DE SOLICITUDES
    // =====================================================

    val solicitudViewModel: SolicitudViewModel = viewModel(
        factory = SolicitudViewModelFactory(
            sessionManager
        )
    )

    // =====================================================
    // VIEWMODEL DE EQUIPOS
    // =====================================================

    val equipoViewModel: EquipoViewModel = viewModel()

    val equipos by equipoViewModel
        .equipos
        .collectAsStateWithLifecycle()

    // =====================================================
    // VIEWMODEL DE LABORATORIOS
    // =====================================================

    val laboratorioViewModel: LaboratorioViewModel = viewModel()

    val laboratorios by laboratorioViewModel
        .laboratorios
        .collectAsStateWithLifecycle()

    // =====================================================
    // VIEWMODEL DE REGISTRO
    // =====================================================

    val registroViewModel: RegistroViewModel = viewModel(
        factory = RegistroViewModelFactory(
            UsuarioRepository(
                RetrofitInstance.api
            )
        )
    )

    // =====================================================
    // LISTA DE SOLICITUDES
    // =====================================================

    val solicitudes by solicitudViewModel
        .solicitudes
        .collectAsStateWithLifecycle()

    // =====================================================
    // EQUIPO SELECCIONADO
    // =====================================================

    var equipoSeleccionado by remember {
        mutableStateOf<Equipo?>(null)
    }

    // =====================================================
    // NAVEGACIÓN
    // =====================================================

    NavHost(
        navController = navController,
        startDestination = if (sesionActiva) {
            "home"
        } else {
            "login"
        }
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
                },

                onRegistroClick = {

                    navController.navigate("registro")
                }
            )
        }

        // =====================================================
        // REGISTRO
        // =====================================================

        composable("registro") {

            RegistroScreen(

                onRegistroExitoso = { usuario ->

                    registroViewModel.registrarUsuario(
                        usuario
                    )
                },

                onVolverLogin = {

                    navController.popBackStack()
                }
            )
        }

        // =====================================================
        // HOME
        // =====================================================

        composable("home") {

            HomeScreen(

                onCatalogoClick = {

                    navController.navigate(
                        "catalogo"
                    )
                },

                onEquiposClick = {

                    navController.navigate(
                        "equipos"
                    )
                },

                onPrestamosClick = {

                    navController.navigate(
                        "prestamos"
                    )
                },

                onSolicitudesClick = {

                    navController.navigate(
                        "solicitudes"
                    )
                },

                onLaboratoriosClick = {

                    navController.navigate(
                        "laboratorios"
                    )
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

                    navController.navigate(
                        "detalle_equipo"
                    )
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

                        navController.navigate(
                            "nueva_solicitud"
                        )
                    }
                )
            }
        }

        // =====================================================
        // NUEVA SOLICITUD
        // =====================================================

        composable("nueva_solicitud") {

            val estaSancionado =
                sessionManager.estaSancionado()

            val fechaDesbloqueo =
                sessionManager.obtenerFechaDesbloqueo()

            if (estaSancionado) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                ) {

                    Text(
                        text = "Solicitud bloqueada",
                        style = MaterialTheme
                            .typography
                            .headlineMedium
                    )

                    Text(
                        text =
                            "No puedes solicitar equipos porque tienes una sanción activa.",
                        modifier = Modifier.padding(
                            top = 16.dp
                        )
                    )

                    val fecha = SimpleDateFormat(
                        "dd/MM/yyyy HH:mm",
                        Locale.US
                    ).format(
                        Date(fechaDesbloqueo)
                    )

                    Text(
                        text =
                            "Podrás volver a solicitar equipos después de:",
                        modifier = Modifier.padding(
                            top = 16.dp
                        )
                    )

                    Text(
                        text = fecha,
                        style = MaterialTheme
                            .typography
                            .titleMedium,
                        modifier = Modifier.padding(
                            top = 8.dp
                        )
                    )

                    Button(
                        onClick = {

                            navController
                                .popBackStack()
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 24.dp
                            )
                    ) {

                        Text(
                            text = "Volver"
                        )
                    }
                }

            } else {

                equipoSeleccionado?.let { equipo ->

                    SolicitudScreen(
                        equipo = equipo,

                        onSolicitudEnviada = { solicitud ->

                            solicitudViewModel
                                .agregarSolicitud(
                                    solicitud
                                )

                            navController.navigate(
                                "solicitudes"
                            ) {

                                popUpTo(
                                    "nueva_solicitud"
                                ) {
                                    inclusive = true
                                }
                            }
                        }
                    )
                }
            }
        }

// =====================================================
// EQUIPOS
// =====================================================

        composable("equipos") {

            EquiposScreen(
                equipos = equipos,

                // ---------------------------------------------
                // AGREGAR EQUIPO
                // ---------------------------------------------

                onAgregar = { nombre, tipo, codigo ->

                    equipoViewModel.agregarEquipo(
                        nombre = nombre,
                        tipo = tipo,
                        codigo = codigo
                    )
                },

                // ---------------------------------------------
                // EDITAR EQUIPO
                // ---------------------------------------------

                onEditar = {
                        id,
                        nombre,
                        tipo,
                        codigo,
                        estado,
                        disponible ->

                    equipoViewModel.actualizarEquipo(
                        id = id,
                        nombre = nombre,
                        tipo = tipo,
                        codigo = codigo,
                        estado = estado,
                        disponible = disponible
                    )
                },

                // ---------------------------------------------
                // ELIMINAR EQUIPO
                // ---------------------------------------------

                onEliminar = { id ->

                    equipoViewModel.eliminarEquipo(
                        id
                    )
                },

                // ---------------------------------------------
                // REPORTAR INCIDENTE
                // ---------------------------------------------

                onReportarIncidente = {
                        equipo,
                        observacion ->

                    equipoViewModel.reportarIncidente(
                        equipoId = equipo.id,
                        observacion = observacion
                    )
                },

                // ---------------------------------------------
                // FINALIZAR MANTENIMIENTO
                // ---------------------------------------------

                onFinalizarMantenimiento = { equipo ->

                    equipoViewModel.finalizarMantenimiento(
                        equipoId = equipo.id
                    )
                },

                // ---------------------------------------------
                // VOLVER
                // ---------------------------------------------

                onVolver = {

                    navController.popBackStack()
                }
            )
        }

        // =====================================================
        // LABORATORIOS
        // =====================================================

        composable("laboratorios") {

            LaboratoriosScreen(
                laboratorios = laboratorios,

                onAgregar = { nombre, codigo ->

                    laboratorioViewModel
                        .agregarLaboratorio(
                            nombre = nombre,
                            codigo = codigo
                        )
                },

                onEditar = {
                        id,
                        nombre,
                        codigo,
                        estado ->

                    laboratorioViewModel
                        .actualizarLaboratorio(
                            id = id,
                            nombre = nombre,
                            codigo = codigo,
                            estado = estado
                        )
                },

                onEliminar = { id ->

                    laboratorioViewModel
                        .eliminarLaboratorio(
                            id
                        )
                },

                onCambiarEstado = {
                        id,
                        estado ->

                    laboratorioViewModel
                        .cambiarEstado(
                            id = id,
                            estado = estado
                        )
                },

                onVolver = {

                    navController
                        .popBackStack()
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

                    navController
                        .popBackStack()
                },

                onDevolverClick = { solicitud ->

                    solicitudViewModel
                        .devolverPrestamo(
                            solicitud.id
                        )

                    equipoViewModel
                        .actualizarDisponibilidad(
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

                    navController
                        .popBackStack()
                },

                // =================================================
                // APROBAR O RECHAZAR SOLICITUD
                // =================================================

                onCambiarEstado = {
                        solicitudId,
                        nuevoEstado ->

                    solicitudViewModel
                        .cambiarEstado(
                            solicitudId = solicitudId,
                            nuevoEstado = nuevoEstado
                        )

                    val solicitud =
                        solicitudes.find {
                            it.id == solicitudId
                        }

                    solicitud?.let {

                        // =========================================
                        // SOLICITUD APROBADA
                        // =========================================

                        if (
                            nuevoEstado.equals(
                                "Aprobada",
                                ignoreCase = true
                            )
                        ) {

                            equipoViewModel
                                .actualizarDisponibilidad(
                                    idEquipo =
                                        it.equipo.id,
                                    disponible = false
                                )

                            NotificationHelper
                                .programarRecordatorio(
                                    context = context,
                                    fechaDevolucion =
                                        it.fechaDevolucion,
                                    equipo =
                                        it.equipo.nombre,
                                    solicitudId =
                                        it.id
                                )

                            NotificationHelper
                                .mostrarNotificacion(
                                    context = context,
                                    titulo =
                                        "Solicitud aprobada",
                                    mensaje =
                                        "Tu solicitud para ${it.equipo.nombre} fue aprobada.",
                                    id = solicitudId
                                )
                        }

                        // =========================================
                        // SOLICITUD RECHAZADA
                        // =========================================

                        if (
                            nuevoEstado.equals(
                                "Rechazada",
                                ignoreCase = true
                            )
                        ) {

                            NotificationHelper
                                .mostrarNotificacion(
                                    context = context,
                                    titulo =
                                        "Solicitud rechazada",
                                    mensaje =
                                        "Tu solicitud para ${it.equipo.nombre} fue rechazada.",
                                    id = solicitudId
                                )
                        }
                    }
                },

                // =================================================
                // CANCELAR SOLICITUD
                // =================================================

                onCancelarSolicitud = {
                        solicitudId ->

                    solicitudViewModel
                        .cancelarSolicitud(
                            solicitudId
                        )
                },

                // =================================================
                // RECHAZAR CON MOTIVO
                // =================================================

                onRechazarSolicitud = {
                        solicitudId,
                        motivo ->

                    solicitudViewModel
                        .rechazarSolicitud(
                            solicitudId = solicitudId,
                            motivoRechazo = motivo
                        )

                    NotificationHelper
                        .mostrarNotificacion(
                            context = context,
                            titulo =
                                "Solicitud rechazada",
                            mensaje =
                                "Tu solicitud fue rechazada. Motivo: $motivo",
                            id = solicitudId
                        )
                }
            )
        }
    }
}