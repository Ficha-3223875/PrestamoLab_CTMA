package com.ctma.prestamolabctma.data.repository

import com.ctma.prestamolabctma.data.local.entity.dao.UsuarioDao
import com.ctma.prestamolabctma.model.Usuario

class LoginRepository(
    private val usuarioDao: UsuarioDao
) {

    suspend fun iniciarSesion(
        correo: String,
        password: String
    ): Result<Usuario> {

        return try {

            val usuario =
                usuarioDao.iniciarSesion(
                    correo = correo.trim(),
                    password = password
                )

            if (usuario == null) {

                Result.failure(
                    Exception(
                        "Datos incorrectos. Verifica el correo y la contraseña"
                    )
                )

            } else {

                Result.success(
                    Usuario(
                        documento = usuario.documento,
                        nombre = usuario.nombre,
                        correo = usuario.correo,
                        password = usuario.password,
                        programa = usuario.programa,
                        ficha = usuario.ficha,
                        rol = usuario.rol
                    )
                )
            }

        } catch (e: Exception) {

            Result.failure(
                Exception(
                    e.message
                        ?: "No se pudo iniciar sesión"
                )
            )
        }
    }
}