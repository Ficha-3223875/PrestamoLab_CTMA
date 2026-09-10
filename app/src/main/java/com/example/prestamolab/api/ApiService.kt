package com.example.prestamolab.api

import com.example.prestamolab.model.Estudiante
import com.example.prestamolab.model.LoginRequest
import com.example.prestamolab.model.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("estudiantes/registro")
    fun registrarEstudiante(@Body estudiante: Estudiante): Call<Void>

    @POST("estudiantes/login")
    fun iniciarSesion(@Body request: LoginRequest): Call<LoginResponse>
}