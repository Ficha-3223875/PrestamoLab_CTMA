package com.example.prestamolab.repository

import android.content.Context
import com.example.prestamolab.api.RetrofitClient
import com.example.prestamolab.data.local.AppDatabase
import com.example.prestamolab.data.local.PrestamoEntity
import com.example.prestamolab.model.PrestamoHistorial

class PrestamoRepository(private val context: Context) {

    private val db = AppDatabase.getDatabase(context)

    suspend fun obtenerHistorial(email: String): List<PrestamoHistorial> {
        return try {
            val response = RetrofitClient.instance.getHistorial(email)
            if (response.isSuccessful && response.body() != null) {
                val remotos = response.body()!!

                // CA-14.1 Guardar en Caché Room
                val entidades = remotos.map {
                    PrestamoEntity(it.idSolicitud, nombreEquipo = it.itemOEspacio, it.fechaSolicitud, it.fechaDevolucion, it.estado)
                }
                db.prestamoDao().insertarLista(entidades)
                remotos
            } else {
                obtenerDesdeCacheLocal()
            }
        } catch (e: Exception) {
            // CA-14.1 Recuperar de caché local si no hay conexión
            obtenerDesdeCacheLocal()
        }
    }

    private suspend fun obtenerDesdeCacheLocal(): List<PrestamoHistorial> {
        return db.prestamoDao().obtenerTodo().map {
            PrestamoHistorial(it.idSolicitud, it.nombreEquipo, it.fechaSolicitud, it.fechaDevolucion, it.estado)
        }
    }
}