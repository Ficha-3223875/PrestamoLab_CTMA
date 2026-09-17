package com.ctma.prestamolabctma.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class UsuarioEntity(
    @PrimaryKey
    val documento: String,
    val nombre: String,
    val correo: String,
    val password: String,
    val programa: String,
    val ficha: String,
    val rol: String
)