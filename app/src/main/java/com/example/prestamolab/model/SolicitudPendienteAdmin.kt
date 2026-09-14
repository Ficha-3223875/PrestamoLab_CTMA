package com.example.prestamolab.model

data class SolicitudPendienteAdmin(
    val idSolicitud: String,
    val nombreAprendiz: String,
    val correoAprendiz: String,
    val equipoOEspacio: String,
    val fechaHoraLlegada: String, // Para orden cronológico (CA-08.1)
    var estado: String = "Pendiente"
)