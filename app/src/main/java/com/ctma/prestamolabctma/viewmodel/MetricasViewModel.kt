package com.ctma.prestamolabctma.viewmodel

import androidx.lifecycle.ViewModel
import com.ctma.prestamolabctma.model.Solicitud
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class Metricas(
    val totalSolicitudes: Int = 0,
    val pendientes: Int = 0,
    val aprobadas: Int = 0,
    val rechazadas: Int = 0,
    val canceladas: Int = 0,
    val prestamosActivos: Int = 0,
    val devueltos: Int = 0
)

class MetricasViewModel : ViewModel() {

    private val _metricas =
        MutableStateFlow(Metricas())

    val metricas: StateFlow<Metricas> =
        _metricas

    fun calcularMetricas(solicitudes: List<Solicitud>) {

        _metricas.value = Metricas(
            totalSolicitudes = solicitudes.size,

            pendientes = solicitudes.count {
                it.estado.equals(
                    "Pendiente",
                    ignoreCase = true
                )
            },

            aprobadas = solicitudes.count {
                it.estado.equals(
                    "Aprobada",
                    ignoreCase = true
                )
            },

            rechazadas = solicitudes.count {
                it.estado.equals(
                    "Rechazada",
                    ignoreCase = true
                )
            },

            canceladas = solicitudes.count {
                it.estado.equals(
                    "Cancelada",
                    ignoreCase = true
                )
            },

            prestamosActivos = solicitudes.count {
                it.estado.equals(
                    "En Préstamo",
                    ignoreCase = true
                )
            },

            devueltos = solicitudes.count {
                it.estado.equals(
                    "Devuelto",
                    ignoreCase = true
                )
            }
        )
    }
}