package com.example.prestamolab.model

data class PrestamoHistorial(
    val idSolicitud: String,
    val itemOEspacio: String,
    val fechaSolicitud: String,
    val fechaDevolucion: String,
    var estado: String // Cambiado a 'var' para permitir actualizar el estado a "Cancelada" (HU-06)
)