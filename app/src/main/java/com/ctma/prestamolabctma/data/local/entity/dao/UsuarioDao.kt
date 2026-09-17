package com.ctma.prestamolabctma.data.local.entity.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ctma.prestamolabctma.data.local.entity.UsuarioEntity

@Dao
interface UsuarioDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertarUsuario(usuario: UsuarioEntity)

    @Query(
        "SELECT * FROM usuarios " +
                "WHERE correo = :correo " +
                "LIMIT 1"
    )
    suspend fun obtenerPorCorreo(
        correo: String
    ): UsuarioEntity?

    @Query(
        "SELECT * FROM usuarios " +
                "WHERE correo = :correo " +
                "AND password = :password " +
                "LIMIT 1"
    )
    suspend fun iniciarSesion(
        correo: String,
        password: String
    ): UsuarioEntity?

    @Query(
        "SELECT * FROM usuarios " +
                "WHERE documento = :documento " +
                "LIMIT 1"
    )
    suspend fun obtenerPorDocumento(
        documento: String
    ): UsuarioEntity?
}