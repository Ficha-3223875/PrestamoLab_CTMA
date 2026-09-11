package com.example.prestamolab.model

data class SolicitudPrestamoResponse(
    val idSolicitud: String,
    val estado: String, // "Pendiente"
    val mensaje: String,
    val itemOEspacio: String,
    val fecha: String,
    val horaInicio: String,
    val horaFin: String
)