package com.example.prestamolab.util

import android.content.Context
import android.widget.Toast
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

object NetworkHelper {

    suspend fun <T> ejecutarPeticionSegura(
        context: Context,
        call: suspend () -> Response<T>,
        onExito: (T?) -> Unit
    ) {
        try {
            val response = call()
            if (response.isSuccessful) {
                onExito(response.body())
            } else {
                val mensajeError = when (response.code()) {
                    400 -> "Solicitud incorrecta (400)"
                    401 -> "No autorizado (401)"
                    404 -> "Recurso no encontrado (404)"
                    500 -> "Error interno del servidor (500)"
                    else -> "Error en servidor: ${response.code()}"
                }
                Toast.makeText(context, mensajeError, Toast.LENGTH_LONG).show()
            }
        } catch (e: SocketTimeoutException) {
            Toast.makeText(context, "Tiempo de espera agotado (Timeout)", Toast.LENGTH_LONG).show()
        } catch (e: IOException) {
            Toast.makeText(context, "Sin conexión a Internet", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Error inesperado: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
        }
    }
}