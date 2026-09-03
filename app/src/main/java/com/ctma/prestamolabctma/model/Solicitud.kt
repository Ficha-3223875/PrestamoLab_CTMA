package com.ctma.prestamolabctma.model

data class Solicitud(
    val id: Int,
    val equipo: Equipo,
    val fechaPrestamo: String,
    val fechaDevolucion: String,
    val motivo: String,
    val estado: String
)