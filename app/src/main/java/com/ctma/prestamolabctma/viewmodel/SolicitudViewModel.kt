package com.ctma.prestamolabctma.viewmodel

import androidx.lifecycle.ViewModel
import com.ctma.prestamolabctma.model.Solicitud
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SolicitudViewModel : ViewModel() {

    private val _solicitudes =
        MutableStateFlow<List<Solicitud>>(emptyList())

    val solicitudes: StateFlow<List<Solicitud>> = _solicitudes

    // Agregar una nueva solicitud
    fun agregarSolicitud(solicitud: Solicitud) {

        _solicitudes.value =
            _solicitudes.value + solicitud
    }

    // Cambiar el estado de una solicitud
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

    // Devolver un préstamo
    fun devolverPrestamo(
        solicitudId: Int
    ) {

        cambiarEstado(
            solicitudId = solicitudId,
            nuevoEstado = "Devuelto"
        )
    }
}