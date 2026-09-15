package com.example.prestamolab.model

data class DevolucionRequest(
    val idSolicitud: String
)

data class DevolucionResponse(
    val exito: Boolean,
    val mensaje: String
)