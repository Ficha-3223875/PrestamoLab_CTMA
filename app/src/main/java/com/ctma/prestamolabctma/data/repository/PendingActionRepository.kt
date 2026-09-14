package com.ctma.prestamolabctma.data.repository

import com.ctma.prestamolabctma.data.local.entity.PendingActionEntity
import com.ctma.prestamolabctma.data.local.entity.dao.PendingActionDao
import kotlinx.coroutines.flow.Flow

class PendingActionRepository(
    private val pendingActionDao: PendingActionDao
) {

    fun obtenerAccionesPendientes():
            Flow<List<PendingActionEntity>> {

        return pendingActionDao
            .obtenerAccionesPendientes()
    }

    suspend fun guardarAccion(
        tipo: String,
        solicitudId: Int,
        estado: String = "PENDIENTE"
    ) {

        pendingActionDao.insertarAccion(

            PendingActionEntity(
                tipo = tipo,
                solicitudId = solicitudId,
                estado = estado
            )
        )
    }

    suspend fun marcarComoSincronizada(
        id: Int
    ) {

        pendingActionDao
            .marcarComoSincronizada(id)
    }

    suspend fun eliminarAccion(
        accion: PendingActionEntity
    ) {

        pendingActionDao
            .eliminarAccion(accion)
    }

    suspend fun eliminarTodas() {

        pendingActionDao
            .eliminarTodas()
    }
}