package com.example.prestamolab.model

data class CatalogoItem(
    val id: String,
    val nombre: String,
    val categoria: String,
    val marca: String,
    val modelo: String,
    val descripcion: String,
    val disponible: Boolean
)