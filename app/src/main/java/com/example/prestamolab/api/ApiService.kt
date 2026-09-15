package com.example.prestamolab.api

import com.example.prestamolab.model.CatalogoItem
import com.example.prestamolab.model.DevolucionRequest
import com.example.prestamolab.model.Estudiante
import com.example.prestamolab.model.EstadoUsuarioResponse
import com.example.prestamolab.model.IncidenciaRequest
import com.example.prestamolab.model.InventarioResponse
import com.example.prestamolab.model.LoginRequest
import com.example.prestamolab.model.LoginResponse
import com.example.prestamolab.model.PrestamoHistorial
import com.example.prestamolab.model.RecursoInventario
import com.example.prestamolab.model.ReporteResumen
import com.example.prestamolab.model.SancionResponse
import com.example.prestamolab.model.SolicitudPendienteAdmin
import com.example.prestamolab.model.SolicitudPrestamoRequest
import com.example.prestamolab.model.SolicitudPrestamoResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // Registro de Estudiante
    @POST("api/estudiantes")
    fun registrarEstudiante(@Body estudiante: Estudiante): Call<Void>

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("api/catalogo")
    suspend fun getCatalogo(): Response<List<CatalogoItem>>

    @GET("api/historial/{email}")
    suspend fun getHistorial(@Path("email") email: String): Response<List<PrestamoHistorial>>

    @POST("api/solicitudes")
    suspend fun crearSolicitud(@Body request: SolicitudPrestamoRequest): Response<SolicitudPrestamoResponse>

    @GET("api/admin/solicitudes/pendientes")
    suspend fun getSolicitudesPendientes(): Response<List<SolicitudPendienteAdmin>>

    @POST("api/admin/devolucion")
    suspend fun registrarDevolucion(@Body request: DevolucionRequest): Response<InventarioResponse>

    @POST("api/admin/incidencia")
    suspend fun registrarIncidencia(@Body request: IncidenciaRequest): Response<IncidenciaRequest>

    @GET("api/usuarios/estado/{email}")
    suspend fun verificarEstadoUsuario(@Path("email") email: String): Response<EstadoUsuarioResponse>

    @POST("api/admin/prestamos/devolver-sancionar")
    suspend fun registrarDevolucionConSancion(@Body request: DevolucionRequest): Response<SancionResponse>

    // HU-13: CRUD de Inventario y Laboratorios
    @GET("api/admin/inventario")
    suspend fun getInventario(): Response<List<RecursoInventario>>

    @POST("api/admin/inventario")
    suspend fun crearRecurso(@Body recurso: RecursoInventario): Response<InventarioResponse>

    @PUT("api/admin/inventario/{id}")
    suspend fun actualizarRecurso(@Path("id") id: String, @Body recurso: RecursoInventario): Response<InventarioResponse>

    @DELETE("api/admin/inventario/{id}")
    suspend fun eliminarRecurso(@Path("id") id: String): Response<InventarioResponse>

    // HU-15: Métricas y Reportes
    @GET("api/reportes/resumen")
    suspend fun obtenerReporteResumen(
        @Query("fechaInicio") fechaInicio: String?,
        @Query("fechaFin") fechaFin: String?,
        @Query("ficha") ficha: String?
    ): Response<ReporteResumen>
}