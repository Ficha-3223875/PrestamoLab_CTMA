package com.ctma.prestamolabctma.viewmodel

import androidx.lifecycle.ViewModel
import com.ctma.prestamolabctma.data.session.SessionManager
import com.ctma.prestamolabctma.model.Solicitud
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SolicitudViewModel(
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _solicitudes =
        MutableStateFlow<List<Solicitud>>(emptyList())

    val solicitudes: StateFlow<List<Solicitud>> =
        _solicitudes

    fun agregarSolicitud(solicitud: Solicitud) {

        _solicitudes.value =
            _solicitudes.value + solicitud
    }

    fun cambiarEstado(
        solicitudId: Int,
        nuevoEstado: String
    ) {

        _solicitudes.value =
            _solicitudes.value.map { solicitud ->

                if (solicitud.id == solicitudId) {

                    solicitud.copy(
                        estado = nuevoEstado
                    )

                } else {

                    solicitud
                }
            }
    }

    fun cancelarSolicitud(
        solicitudId: Int
    ) {

        _solicitudes.value =
            _solicitudes.value.map { solicitud ->

                if (solicitud.id == solicitudId) {

                    solicitud.copy(
                        estado = "Cancelada"
                    )

                } else {

                    solicitud
                }
            }
    }

    fun devolverPrestamo(
        solicitudId: Int
    ) {

        val solicitud = _solicitudes.value.find {
            it.id == solicitudId
        }

        if (solicitud != null) {

            if (esDevolucionTardia(solicitud)) {

                val tresDiasEnMillis =
                    3L * 24L * 60L * 60L * 1000L

                val fechaDesbloqueo =
                    System.currentTimeMillis() +
                            tresDiasEnMillis

                sessionManager.guardarSancion(
                    fechaDesbloqueo
                )
            }

            _solicitudes.value =
                _solicitudes.value.map {

                    if (it.id == solicitudId) {

                        it.copy(
                            estado = "Devuelto"
                        )

                    } else {

                        it
                    }
                }
        }
    }

    private fun esDevolucionTardia(
        solicitud: Solicitud
    ): Boolean {

        return try {

            val formato = SimpleDateFormat(
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

    fun rechazarSolicitud(
        solicitudId: Int,
        motivoRechazo: String
    ) {

        _solicitudes.value =
            _solicitudes.value.map { solicitud ->

                if (solicitud.id == solicitudId) {

                    solicitud.copy(
                        estado = "Rechazada",
                        motivoRechazo = motivoRechazo
                    )

                } else {

                    solicitud
                }
            }
    }

    fun iniciarPrestamo(
        solicitudId: Int
    ) {

        _solicitudes.value =
            _solicitudes.value.map { solicitud ->

                if (solicitud.id == solicitudId) {

                    solicitud.copy(
                        estado = "En Préstamo"
                    )

                } else {

                    solicitud
                }
            }
    }
}