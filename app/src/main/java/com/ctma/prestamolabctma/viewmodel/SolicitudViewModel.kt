package com.ctma.prestamolabctma.viewmodel

import androidx.lifecycle.ViewModel
import com.ctma.prestamolabctma.model.Solicitud
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SolicitudViewModel : ViewModel() {

    private val _solicitudes =
        MutableStateFlow<List<Solicitud>>(emptyList())

    val solicitudes: StateFlow<List<Solicitud>> =
        _solicitudes

    fun agregarSolicitud(
        solicitud: Solicitud
    ) {

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

    fun devolverPrestamo(
        solicitudId: Int
    ) {

        _solicitudes.value =
            _solicitudes.value.map { solicitud ->

                if (solicitud.id == solicitudId) {

                    solicitud.copy(
                        estado = "Devuelto"
                    )

                } else {

                    solicitud
                }
            }
    }
}