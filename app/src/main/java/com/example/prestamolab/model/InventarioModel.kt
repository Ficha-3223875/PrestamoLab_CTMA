package com.example.prestamolab.model

import com.google.gson.annotations.SerializedName

data class RecursoInventario(
    @SerializedName("id") val id: String,
    @SerializedName("codigo") var codigo: String,
    @SerializedName("nombre") var nombre: String,
    @SerializedName("tipo") var tipo: String, // "Equipo" o "Laboratorio"
    @SerializedName("estado") var estado: String, // "Disponible", "Mantenimiento", "Ocupado"
    @SerializedName("disponibilidad") var disponibilidad: Boolean
)

data class InventarioResponse(
    @SerializedName("exito") val exito: Boolean,
    @SerializedName("mensaje") val mensaje: String
)