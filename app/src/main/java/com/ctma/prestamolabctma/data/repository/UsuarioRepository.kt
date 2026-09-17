package com.ctma.prestamolabctma.data.repository

import com.ctma.prestamolabctma.data.local.entity.UsuarioEntity
import com.ctma.prestamolabctma.data.local.entity.dao.UsuarioDao
import com.ctma.prestamolabctma.model.Usuario

class UsuarioRepository(
    private val usuarioDao: UsuarioDao
) {

    suspend fun registrarUsuario(
        usuario: Usuario
    ): Result<Usuario> {

        return try {

            val usuarioExistente =
                usuarioDao.obtenerPorCorreo(
                    usuario.correo
                )

            if (usuarioExistente != null) {
                return Result.failure(
                    Exception(
                        "Ya existe un usuario registrado con ese correo"
                    )
                )
            }

            val documentoExistente =
                usuarioDao.obtenerPorDocumento(
                    usuario.documento
                )

            if (documentoExistente != null) {
                return Result.failure(
                    Exception(
                        "Ya existe un usuario registrado con ese documento"
                    )
                )
            }

            val usuarioEntity = UsuarioEntity(
                documento = usuario.documento,
                nombre = usuario.nombre,
                correo = usuario.correo,
                password = usuario.password,
                programa = usuario.programa,
                ficha = usuario.ficha,
                rol = usuario.rol
            )

            usuarioDao.insertarUsuario(
                usuarioEntity
            )

            Result.success(usuario)

        } catch (e: Exception) {

            Result.failure(
                Exception(
                    e.message
                        ?: "No se pudo registrar el usuario"
                )
            )
        }
    }
}