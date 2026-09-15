package com.example.prestamolab.model

data class SolicitudPendienteAdmin(
    val idSolicitud: String,
    val nombreAprendiz: String,
    val correoAprendiz: String,
    var equipoOEspacio: String,
    val fechaHoraLlegada: String,
    var estado: String = "Pendiente",
    var tiempoInicioMillis: Long = 0L // CA-09.1: Para iniciar el temporizador oficial
)