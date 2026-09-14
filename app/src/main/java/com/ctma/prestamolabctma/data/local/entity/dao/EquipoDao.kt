package com.ctma.prestamolabctma.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ctma.prestamolabctma.data.local.entity.EquipoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EquipoDao {

    @Query("SELECT * FROM equipos ORDER BY id ASC")
    fun obtenerEquipos(): Flow<List<EquipoEntity>>

    @Query("SELECT * FROM equipos WHERE id = :id LIMIT 1")
    suspend fun obtenerEquipoPorId(id: Int): EquipoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarEquipo(equipo: EquipoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarEquipos(equipos: List<EquipoEntity>)

    @Update
    suspend fun actualizarEquipo(equipo: EquipoEntity)

    @Delete
    suspend fun eliminarEquipo(equipo: EquipoEntity)
}