package com.ctma.prestamolabctma.viewmodel

import androidx.lifecycle.ViewModel
import com.ctma.prestamolabctma.model.Equipo
import com.ctma.prestamolabctma.model.Incidente
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class EquipoViewModel : ViewModel() {

    private val _equipos = MutableStateFlow(
        listOf(
            Equipo(
                id = 1,
                nombre = "Laptop Lenovo",
                tipo = "Computador",
                disponible = true
            ),
            Equipo(
                id = 2,
                nombre = "Proyector Epson",
                tipo = "Proyector",
                disponible = true
            ),
            Equipo(
                id = 3,
                nombre = "Cámara Sony",
                tipo = "Cámara",
                disponible = true
            ),
            Equipo(
                id = 4,
                nombre = "Tablet Samsung",
                tipo = "Tablet",
                disponible = true
            )
        )
    )

    private val _incidentes =
        MutableStateFlow<List<Incidente>>(emptyList())

    val equipos: StateFlow<List<Equipo>> = _equipos

    val incidentes: StateFlow<List<Incidente>> = _incidentes

    fun actualizarDisponibilidad(
        idEquipo: Int,
        disponible: Boolean
    ) {

        _equipos.value = _equipos.value.map { equipo ->

            if (equipo.id == idEquipo) {

                equipo.copy(
                    disponible = disponible,
                    estado = if (disponible) {
                        "Disponible"
                    } else {
                        "No disponible"
                    }
                )

            } else {

                equipo
            }
        }
    }

    fun reportarIncidente(
        equipoId: Int,
        observacion: String
    ) {

        val equipo = _equipos.value.find {
            it.id == equipoId
        }

        if (equipo != null) {

            val incidente = Incidente(
                id = System.currentTimeMillis().toInt(),
                equipoId = equipo.id,
                equipoNombre = equipo.nombre,
                observacion = observacion,
                fecha = java.text.SimpleDateFormat(
                    "yyyy-MM-dd HH:mm",
                    java.util.Locale.getDefault()
                ).format(java.util.Date())
            )

            _incidentes.value =
                _incidentes.value + incidente

            _equipos.value =
                _equipos.value.map {

                    if (it.id == equipoId) {

                        it.copy(
                            disponible = false,
                            estado = "En Mantenimiento"
                        )

                    } else {

                        it
                    }
                }
        }
    }

    fun finalizarMantenimiento(
        equipoId: Int
    ) {

        _equipos.value =
            _equipos.value.map {

                if (it.id == equipoId) {

                    it.copy(
                        disponible = true,
                        estado = "Disponible"
                    )

                } else {

                    it
                }
            }
    }
}