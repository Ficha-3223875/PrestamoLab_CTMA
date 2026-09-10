package com.example.prestamolabctma.api

import com.example.prestamolabctma.model.Estudiante
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("estudiantes/registro")
    fun registrarEstudiante(@Body estudiante: Estudiante): Call<Void>

    companion object {
        private const val BASE_URL = "http://10.0.2.2:3000/api/" // Ajusta según la IP de tu servidor

        fun create(): ApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}