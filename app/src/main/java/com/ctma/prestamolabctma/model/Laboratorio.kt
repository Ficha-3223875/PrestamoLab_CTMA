package com.ctma.prestamolabctma.model

data class Laboratorio(
    val id: Int,
    val nombre: String,
    val codigo: String,
    val estado: String = "Disponible"
)