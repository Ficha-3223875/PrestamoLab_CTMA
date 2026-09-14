package com.example.prestamolab.api

import com.example.prestamolab.model.CatalogoItem
import com.example.prestamolab.model.Estudiante
import com.example.prestamolab.model.LoginRequest
import com.example.prestamolab.model.LoginResponse
import com.example.prestamolab.model.PrestamoHistorial
import com.example.prestamolab.model.SolicitudPrestamoRequest
import com.example.prestamolab.model.SolicitudPrestamoResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    // HU-01 / Registro
    @POST("api/estudiantes/registro")
    fun registrarEstudiante(@Body estudiante: Estudiante): Call<Void>

    // HU-02 / Login
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    // HU-03 / Solicitud de Préstamo (Adaptado para soporte de Call/.enqueue)
    @POST("api/prestamos/solicitar")
    fun solicitarPrestamo(@Body request: SolicitudPrestamoRequest): Call<SolicitudPrestamoResponse>

    // HU-04 y HU-05 / Catálogo
    @GET("api/catalogo")
    suspend fun getCatalogo(): Response<List<CatalogoItem>>

    // HU-04 y HU-05 / Historial
    @GET("api/prestamos/historial")
    suspend fun getHistorial(): Response<List<PrestamoHistorial>>
}