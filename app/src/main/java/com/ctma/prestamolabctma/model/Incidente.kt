package com.ctma.prestamolabctma.model

data class Incidente(
    val id: Int,
    val equipoId: Int,
    val equipoNombre: String,
    val observacion: String,
    val fecha: String
)