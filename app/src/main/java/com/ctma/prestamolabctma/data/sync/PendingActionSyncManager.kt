package com.ctma.prestamolabctma.data.sync

import com.ctma.prestamolabctma.data.repository.PendingActionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PendingActionSyncManager(
    private val pendingActionRepository: PendingActionRepository
) {

    private var syncJob: Job? = null

    fun iniciarSincronizacion(
        scope: CoroutineScope,
        conexion: kotlinx.coroutines.flow.Flow<Boolean>
    ) {

        syncJob?.cancel()

        syncJob = scope.launch(
            Dispatchers.IO
        ) {

            conexion.collectLatest { conectado ->

                if (conectado) {

                    sincronizarPendientes()
                }
            }
        }
    }

    private suspend fun sincronizarPendientes() {

        pendingActionRepository
            .obtenerAccionesPendientes()
            .collectLatest { acciones ->

                acciones.forEach { accion ->

                    pendingActionRepository
                        .marcarComoSincronizada(
                            accion.id
                        )
                }
            }
    }

    fun detenerSincronizacion() {

        syncJob?.cancel()
        syncJob = null
    }
}