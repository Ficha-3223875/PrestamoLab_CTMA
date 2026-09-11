package com.example.prestamolab.model

data class CatalogoItem(
    val id: String,
    val nombre: String,
    val categoria: String,        // Ej: "Equipos de Cómputo", "Medición", "Laboratorio"
    val marca: String,
    val modelo: String,
    val caracteristicas: String,  // Detalles técnicos
    val disponible: Boolean
)