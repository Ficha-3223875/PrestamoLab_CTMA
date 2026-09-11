package com.example.prestamolab.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // Reemplaza esta URL por la IP o dominio real de tu backend cuando esté desplegado.
    // Ejemplo local con emulador Android Studio: "http://10.0.2.2:8080/api/"
    // Ejemplo servidor en la nube: "https://mi-backend-prestamolab.onrender.com/api/"
    private const val BASE_URL = "http://10.0.2.2:8080/api/"

    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}