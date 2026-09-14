package com.ctma.prestamolabctma.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ctma.prestamolabctma.data.local.AppDatabase
import com.ctma.prestamolabctma.data.repository.SolicitudRepository
import com.ctma.prestamolabctma.data.session.SessionManager
import com.ctma.prestamolabctma.model.Solicitud
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SolicitudViewModel(
    application: Application,
    private val sessionManager: SessionManager
) : AndroidViewModel(application) {

    private val solicitudRepository: SolicitudRepository

    private val _solicitudes =
        MutableStateFlow<List<Solicitud>>(emptyList())

    val solicitudes: StateFlow<List<Solicitud>> =
        _solicitudes

    init {

        val database =
            AppDatabase.getDatabase(application)

        solicitudRepository =
            SolicitudRepository(
                solicitudDao = database.solicitudDao(),
                equipoDao = database.equipoDao()
            )

        cargarSolicitudes()
    }

    // =====================================================
    // CARGAR SOLICITUDES DESDE ROOM
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

            solicitudRepository.guardarSolicitud(
                solicitud
            )
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

            solicitudRepository.actualizarSolicitud(
                actualizada
            )
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

            solicitudRepository.actualizarSolicitud(
                actualizada
            )
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

            solicitudRepository.actualizarSolicitud(
                actualizada
            )
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