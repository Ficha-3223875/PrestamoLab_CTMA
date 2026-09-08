package com.ctma.prestamolabctma.data.repository

import com.ctma.prestamolabctma.data.api.ApiService
import com.ctma.prestamolabctma.model.Usuario

class UsuarioRepository(
    private val apiService: ApiService
) {

    suspend fun registrarUsuario(
        usuario: Usuario
    ): Result<Usuario> {

        return try {

            val response = apiService.registrarUsuario(usuario)

            when {
                response.isSuccessful -> {

                    val usuarioRegistrado = response.body()

                    if (usuarioRegistrado != null) {
                        Result.success(usuarioRegistrado)
                    } else {
                        Result.failure(
                            Exception("La respuesta del servidor está vacía")
                        )
                    }
                }

                response.code() == 400 -> {
                    Result.failure(
                        Exception(
                            "Datos inválidos. Verifica la información ingresada"
                        )
                    )
                }

                response.code() == 500 -> {
                    Result.failure(
                        Exception(
                            "Error interno del servidor. Intenta nuevamente"
                        )
                    )
                }

                else -> {
                    Result.failure(
                        Exception(
                            "Error del servidor: ${response.code()}"
                        )
                    )
                }
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