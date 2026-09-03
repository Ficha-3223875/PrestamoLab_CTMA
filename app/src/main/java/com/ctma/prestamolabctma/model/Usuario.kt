package com.ctma.prestamolabctma.model

data class Usuario(
    val documento: String,
    val nombre: String,
    val correo: String,
    val password: String,
    val programa: String,
    val ficha: String,
    val rol: String = "Aprendiz"
)