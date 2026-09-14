package com.ctma.prestamolabctma.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "solicitudes")
data class SolicitudEntity(

    @PrimaryKey
    val id: Int,

    val equipoId: Int,

    val fechaPrestamo: String,

    val fechaDevolucion: String,

    val motivo: String,

    val estado: String,

    val motivoRechazo: String
)