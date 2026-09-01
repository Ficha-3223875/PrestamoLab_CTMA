package com.ctma.prestamolabctma.data

import com.ctma.prestamolabctma.model.Estudiante
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("estudiantes")
    suspend fun registrarEstudiante(
        @Body estudiante: Estudiante
    ): Response<Estudiante>
}