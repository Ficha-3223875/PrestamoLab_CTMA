package com.example.prestamolab.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PrestamoDao {
    @Query("SELECT * FROM historial_prestamos")
    suspend fun obtenerTodo(): List<PrestamoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarLista(lista: List<PrestamoEntity>)
}

@Dao
interface PeticionPendienteDao {
    @Query("SELECT * FROM peticiones_pendientes ORDER BY timestamp ASC")
    suspend fun obtenerPendientes(): List<PeticionPendienteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(peticion: PeticionPendienteEntity)

    @Query("DELETE FROM peticiones_pendientes WHERE id = :id")
    suspend fun eliminarPorId(id: Int)
}