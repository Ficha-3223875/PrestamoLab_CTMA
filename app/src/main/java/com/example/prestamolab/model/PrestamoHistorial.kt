package com.example.prestamolab.model

data class PrestamoHistorial(
    val idSolicitud: String,
    val itemOEspacio: String,
    val fechaSolicitud: String,
    val fechaLimiteDevolucion: String,
    val estado: String
)