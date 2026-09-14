package com.ctma.prestamolabctma.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ctma.prestamolabctma.data.local.AppDatabase
import com.ctma.prestamolabctma.data.network.ConnectivityObserver
import com.ctma.prestamolabctma.data.repository.PendingActionRepository
import com.ctma.prestamolabctma.data.repository.SolicitudRepository
import com.ctma.prestamolabctma.data.session.SessionManager
import com.ctma.prestamolabctma.model.Solicitud
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SolicitudViewModel(
    application: Application,
    private val sessionManager: SessionManager
) : AndroidViewModel(application) {

    private val database =
        AppDatabase.getDatabase(application)

    private val solicitudRepository =
        SolicitudRepository(
            solicitudDao = database.solicitudDao(),
            equipoDao = database.equipoDao()
        )

    private val pendingActionRepository =
        PendingActionRepository(
            database.pendingActionDao()
        )

    private val connectivityObserver =
        ConnectivityObserver(application)

    private val _solicitudes =
        MutableStateFlow<List<Solicitud>>(emptyList())

    val solicitudes: StateFlow<List<Solicitud>> =
        _solicitudes

    init {
        cargarSolicitudes()
    }

    // =====================================================
    // CARGAR SOLICITUDES
    // =====================================================

    private fun cargarSolicitudes() {

        viewModelScope.launch {

            solicitudRepository
                .obtenerSolicitudes()
                .collect { solicitudes ->

                    _solicitudes.value =
                        solicitudes
                }
        }
    }

    // =====================================================
    // AGREGAR SOLICITUD
    // =====================================================

    fun agregarSolicitud(
        solicitud: Solicitud
    ) {

        viewModelScope.launch {

            solicitudRepository
                .guardarSolicitud(
                    solicitud
                )

            if (!connectivityObserver.estaConectado()) {

                pendingActionRepository
                    .guardarAccion(
                        tipo = "CREAR_SOLICITUD",
                        solicitudId = solicitud.id
                    )
            }
        }
    }

    // =====================================================
    // CAMBIAR ESTADO
    // =====================================================

    fun cambiarEstado(
        solicitudId: Int,
        nuevoEstado: String
    ) {

        val solicitud =
            _solicitudes.value.find {
                it.id == solicitudId
            }

        if (solicitud == null) {
            return
        }

        val actualizada =
            solicitud.copy(
                estado = nuevoEstado
            )

        viewModelScope.launch {

            solicitudRepository
                .actualizarSolicitud(
                    actualizada
                )

            if (!connectivityObserver.estaConectado()) {

                pendingActionRepository
                    .guardarAccion(
                        tipo = "CAMBIAR_ESTADO",
                        solicitudId = solicitudId
                    )
            }
        }
    }

    // =====================================================
    // CANCELAR SOLICITUD
    // =====================================================

    fun cancelarSolicitud(
        solicitudId: Int
    ) {

        cambiarEstado(
            solicitudId = solicitudId,
            nuevoEstado = "Cancelada"
        )
    }

    // =====================================================
    // DEVOLVER PRÉSTAMO
    // =====================================================

    fun devolverPrestamo(
        solicitudId: Int
    ) {

        val solicitud =
            _solicitudes.value.find {
                it.id == solicitudId
            }

        if (solicitud == null) {
            return
        }

        if (esDevolucionTardia(solicitud)) {

            val tresDiasEnMillis =
                3L *
                        24L *
                        60L *
                        60L *
                        1000L

            val fechaDesbloqueo =
                System.currentTimeMillis() +
                        tresDiasEnMillis

            sessionManager.guardarSancion(
                fechaDesbloqueo
            )
        }

        val actualizada =
            solicitud.copy(
                estado = "Devuelto"
            )

        viewModelScope.launch {

            solicitudRepository
                .actualizarSolicitud(
                    actualizada
                )

            if (!connectivityObserver.estaConectado()) {

                pendingActionRepository
                    .guardarAccion(
                        tipo = "DEVOLVER_PRESTAMO",
                        solicitudId = solicitudId
                    )
            }
        }
    }

    // =====================================================
    // VALIDAR DEVOLUCIÓN TARDÍA
    // =====================================================

    private fun esDevolucionTardia(
        solicitud: Solicitud
    ): Boolean {

        return try {

            val formato =
                SimpleDateFormat(
                    "yyyy-MM-dd",
                    Locale.getDefault()
                )

            formato.isLenient = false

            val fechaLimite =
                formato.parse(
                    solicitud.fechaDevolucion
                )

            fechaLimite != null &&
                    Date().after(fechaLimite)

        } catch (e: Exception) {

            false
        }
    }

    // =====================================================
    // RECHAZAR SOLICITUD
    // =====================================================

    fun rechazarSolicitud(
        solicitudId: Int,
        motivoRechazo: String
    ) {

        val solicitud =
            _solicitudes.value.find {
                it.id == solicitudId
            }

        if (solicitud == null) {
            return
        }

        val actualizada =
            solicitud.copy(
                estado = "Rechazada",
                motivoRechazo = motivoRechazo
            )

        viewModelScope.launch {

            solicitudRepository
                .actualizarSolicitud(
                    actualizada
                )

            if (!connectivityObserver.estaConectado()) {

                pendingActionRepository
                    .guardarAccion(
                        tipo = "RECHAZAR_SOLICITUD",
                        solicitudId = solicitudId
                    )
            }
        }
    }

    // =====================================================
    // INICIAR PRÉSTAMO
    // =====================================================

    fun iniciarPrestamo(
        solicitudId: Int
    ) {

        cambiarEstado(
            solicitudId = solicitudId,
            nuevoEstado = "En Préstamo"
        )
    }
}