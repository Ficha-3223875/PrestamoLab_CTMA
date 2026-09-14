package com.ctma.prestamolabctma.data.local.entity.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ctma.prestamolabctma.data.local.entity.PendingActionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PendingActionDao {

    @Query(
        "SELECT * FROM pending_actions " +
                "WHERE estado = 'PENDIENTE' " +
                "ORDER BY fechaCreacion ASC"
    )
    fun obtenerAccionesPendientes():
            Flow<List<PendingActionEntity>>

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertarAccion(
        accion: PendingActionEntity
    )

    @Query(
        "UPDATE pending_actions " +
                "SET estado = 'SINCRONIZADA' " +
                "WHERE id = :id"
    )
    suspend fun marcarComoSincronizada(
        id: Int
    )

    @Delete
    suspend fun eliminarAccion(
        accion: PendingActionEntity
    )

    @Query("DELETE FROM pending_actions")
    suspend fun eliminarTodas()
}