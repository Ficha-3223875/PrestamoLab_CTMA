package com.example.prestamolab.model

data class LoginResponse(
    val mensaje: String,
    val token: String?,
    val usuario: Estudiante?
)