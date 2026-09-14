package com.ctma.prestamolabctma.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ctma.prestamolabctma.data.local.entity.SolicitudEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SolicitudDao {

    @Query("SELECT * FROM solicitudes ORDER BY id ASC")
    fun obtenerSolicitudes(): Flow<List<SolicitudEntity>>

    @Query("SELECT * FROM solicitudes WHERE id = :id LIMIT 1")
    suspend fun obtenerSolicitudPorId(
        id: Int
    ): SolicitudEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarSolicitud(
        solicitud: SolicitudEntity
    )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarSolicitudes(
        solicitudes: List<SolicitudEntity>
    )

    @Update
    suspend fun actualizarSolicitud(
        solicitud: SolicitudEntity
    )

    @Delete
    suspend fun eliminarSolicitud(
        solicitud: SolicitudEntity
    )
}