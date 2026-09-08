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

        // =====================================================
        // USUARIO TEMPORAL PARA PRUEBAS
        // =====================================================

        if (
            correo == "prueba@sena.edu.co" &&
            password == "123456"
        ) {

            val usuarioPrueba = Usuario(
                documento = "0000000000",
                nombre = "Usuario de Prueba",
                correo = "prueba@sena.edu.co",
                password = "123456",
                programa = "ADSO",
                ficha = "0000000",
                rol = "Estudiante"
            )

            return Result.success(usuarioPrueba)
        }

        // =====================================================
        // LOGIN REAL MEDIANTE API
        // =====================================================

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

            when {

                response.isSuccessful -> {

                    val usuarioRespuesta = response.body()

                    if (usuarioRespuesta != null) {

                        Result.success(usuarioRespuesta)

                    } else {

                        Result.failure(
                            Exception(
                                "La respuesta del servidor está vacía"
                            )
                        )
                    }
                }

                response.code() == 400 -> {

                    Result.failure(
                        Exception(
                            "Datos incorrectos. Verifica el correo y la contraseña"
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