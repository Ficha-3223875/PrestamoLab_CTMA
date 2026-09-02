package com.ctma.prestamolabctma.data.repository

import com.ctma.prestamolabctma.data.ApiService
import com.ctma.prestamolabctma.model.Estudiante
import retrofit2.Response

class EstudianteRepository(
    private val apiService: ApiService
) {

    suspend fun registrarEstudiante(
        estudiante: Estudiante
    ): Response<Estudiante> {
        return apiService.registrarEstudiante(estudiante)
    }
}