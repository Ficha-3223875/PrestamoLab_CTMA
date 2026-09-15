package com.example.prestamolab.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // Reemplaza esta URL por la IP o dominio real de tu backend cuando esté desplegado.
    private const val BASE_URL = "http://10.0.2.2:8080/api/"

    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    // Alias para compatibilidad con cualquier vista que use apiService
    val apiService: ApiService get() = instance
}