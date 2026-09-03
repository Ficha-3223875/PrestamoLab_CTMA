package com.ctma.prestamolabctma.viewmodel

import androidx.lifecycle.ViewModel
import com.ctma.prestamolabctma.model.Equipo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class EquipoViewModel : ViewModel() {

    private val _equipos = MutableStateFlow(
        listOf(
            Equipo(
                id = 1,
                nombre = "Portátil Lenovo",
                tipo = "Computador",
                disponible = true
            ),
            Equipo(
                id = 2,
                nombre = "Video Beam",
                tipo = "Proyector",
                disponible = true
            ),
            Equipo(
                id = 3,
                nombre = "Cámara Canon",
                tipo = "Cámara",
                disponible = true
            ),
            Equipo(
                id = 4,
                nombre = "Micrófono",
                tipo = "Audio",
                disponible = true
            )
        )
    )

    val equipos: StateFlow<List<Equipo>> = _equipos

    fun actualizarDisponibilidad(
        idEquipo: Int,
        disponible: Boolean
    ) {

        _equipos.value = _equipos.value.map { equipo ->

            if (equipo.id == idEquipo) {

                equipo.copy(
                    disponible = disponible
                )

            } else {

                equipo
            }
        }
    }
}