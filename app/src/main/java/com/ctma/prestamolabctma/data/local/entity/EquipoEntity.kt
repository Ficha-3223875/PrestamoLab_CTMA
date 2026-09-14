package com.ctma.prestamolabctma.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "equipos")
data class EquipoEntity(

    @PrimaryKey
    val id: Int,

    val nombre: String,

    val tipo: String,

    val codigo: String,

    val disponible: Boolean,

    val estado: String
)