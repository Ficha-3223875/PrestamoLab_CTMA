package com.ctma.prestamolabctma.data.api

import com.ctma.prestamolabctma.model.Usuario
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("usuarios")
    suspend fun registrarUsuario(
        @Body usuario: Usuario
    ): Response<Usuario>

    @POST("login")
    suspend fun iniciarSesion(
        @Body usuario: Usuario
    ): Response<Usuario>
}