package com.example.prestamolab.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "historial_prestamos")
data class PrestamoEntity(
    @PrimaryKey val idSolicitud: String,
    val nombreEquipo: String,
    val fechaSolicitud: String,
    val fechaDevolucion: String,
    val estado: String
)

@Entity(tableName = "peticiones_pendientes")
data class PeticionPendienteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val tipoAccion: String, // "CREAR_SOLICITUD", "DEVOLUCION", etc.
    val payloadJson: String,
    val timestamp: Long = System.currentTimeMillis()
)