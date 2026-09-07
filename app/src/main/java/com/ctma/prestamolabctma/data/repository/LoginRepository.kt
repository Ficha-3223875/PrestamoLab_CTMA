package com.ctma.prestamolabctma.data.repository

import com.ctma.prestamolabctma.data.api.ApiService
import com.ctma.prestamolabctma.model.Usuario

class LoginRepository(
    private val apiService: ApiService
) {

    suspend fun iniciarSesion(
        correo: String,
        password: String
    ): Result<Usuario> {

        return try {

            val usuario = Usuario(
                documento = "",
                nombre = "",
                correo = correo,
                password = password,
                programa = "",
                ficha = "",
                rol = ""
            )

            val response = apiService.iniciarSesion(usuario)

            if (response.isSuccessful) {

                val usuarioRespuesta = response.body()

                if (usuarioRespuesta != null) {
                    Result.success(usuarioRespuesta)
                } else {
                    Result.failure(
                        Exception("La respuesta del servidor está vacía")
                    )
                }

            } else {

                Result.failure(
                    Exception(
                        "Credenciales incorrectas"
                    )
                )
            }

        } catch (e: Exception) {

            Result.failure(
                Exception(
                    "No se pudo conectar con el servidor"
                )
            )
        }
    }
}