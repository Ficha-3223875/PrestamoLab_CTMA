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
import retrofit2.http.Path

interface ApiService {

    @POST("api/estudiantes/registro")
    fun registrarEstudiante(@Body estudiante: Estudiante): Call<Void>

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("api/prestamos/solicitar")
    fun solicitarPrestamo(@Body request: SolicitudPrestamoRequest): Call<SolicitudPrestamoResponse>

    @GET("api/catalogo")
    suspend fun getCatalogo(): Response<List<CatalogoItem>>

    @GET("api/prestamos/historial")
    suspend fun getHistorial(): Response<List<PrestamoHistorial>>

    // HU-06: Endpoint para cancelar solicitud
    @POST("api/prestamos/cancelar/{id}")
    suspend fun cancelarPrestamo(@Path("id") idSolicitud: String): Response<Void>
}