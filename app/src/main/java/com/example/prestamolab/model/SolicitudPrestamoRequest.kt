package com.example.prestamolab.model

data class SolicitudPrestamoRequest(
    val usuarioEmail: String,
    val itemOEspacio: String,
    val fecha: String,
    val horaInicio: String,
    val horaFin: String
)