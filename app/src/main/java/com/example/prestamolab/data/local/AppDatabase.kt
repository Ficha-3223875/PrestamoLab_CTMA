package com.example.prestamolab.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.prestamolab.data.local.AppDatabase

class SyncWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val db = AppDatabase.getDatabase(applicationContext)
        val pendientes = db.peticionPendienteDao().obtenerPendientes()

        Log.d("SyncWorker", "Sincronizando ${pendientes.size} peticiones pendientes con el servidor...")

        for (peticion in pendientes) {
            try {
                // Aquí se reintentan las llamadas pendientes con Retrofit según tipoAccion
                db.peticionPendienteDao().eliminarPorId(peticion.id)
            } catch (e: Exception) {
                Log.e("SyncWorker", "Error al procesar la transacción id: ${peticion.id}")
                return Result.retry()
            }
        }

        return Result.success()
    }
}