package com.example.prestamolab.api

import com.example.prestamolab.model.CatalogoItem
import com.example.prestamolab.model.DevolucionRequest
import com.example.prestamolab.model.DevolucionResponse
import com.example.prestamolab.model.Estudiante
import com.example.prestamolab.model.LoginRequest
import com.example.prestamolab.model.LoginResponse
import com.example.prestamolab.model.PrestamoHistorial
import com.example.prestamolab.model.SolicitudPendienteAdmin
import com.example.prestamolab.model.SolicitudPrestamoRequest
import com.example.prestamolab.model.SolicitudPrestamoResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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

    @POST("api/prestamos/cancelar/{id}")
    suspend fun cancelarPrestamo(@Path("id") idSolicitud: String): Response<Void>

    // HU-08: Admin Pendientes
    @GET("api/admin/prestamos/pendientes")
    suspend fun getSolicitudesPendientes(): Response<List<SolicitudPendienteAdmin>>

    @POST("api/admin/prestamos/{id}/aprobar")
    suspend fun aprobarSolicitud(@Path("id") idSolicitud: String): Response<Void>

    @POST("api/admin/prestamos/{id}/rechazar")
    suspend fun rechazarSolicitud(
        @Path("id") idSolicitud: String,
        @Query("motivo") motivo: String
    ): Response<Void>

    // HU-09: Registrar Entrega e Imprevisto
    @POST("api/admin/prestamos/{id}/entregar")
    suspend fun registrarEntregaFisica(@Path("id") idSolicitud: String): Response<Void>

    @POST("api/admin/prestamos/{id}/reasignar")
    suspend fun reasignarEquipo(
        @Path("id") idSolicitud: String,
        @Query("nuevoEquipoId") nuevoEquipoId: String
    ): Response<Void>

    // HU-10: Registrar retorno de equipos y actualización de stock
    @POST("api/admin/prestamos/devolver")
    suspend fun registrarDevolucion(@Body request: DevolucionRequest): Response<DevolucionResponse>
}