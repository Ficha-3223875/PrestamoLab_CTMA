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

            if (response.isSuccessful) {

                val usuarioRegistrado = response.body()

                if (usuarioRegistrado != null) {

                    Result.success(usuarioRegistrado)

                } else {

                    Result.failure(
                        Exception("La respuesta del servidor está vacía")
                    )
                }

            } else {

                Result.failure(
                    Exception(
                        "Error ${response.code()}: ${response.message()}"
                    )
                )
            }

        } catch (e: Exception) {

            Result.failure(e)
        }
    }
}