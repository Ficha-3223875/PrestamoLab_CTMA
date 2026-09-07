package com.ctma.prestamolabctma.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ctma.prestamolabctma.data.repository.LoginRepository
import com.ctma.prestamolabctma.data.session.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: LoginRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _mensaje = MutableStateFlow("")
    val mensaje: StateFlow<String> = _mensaje

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando

    private val _loginExitoso = MutableStateFlow(false)
    val loginExitoso: StateFlow<Boolean> = _loginExitoso

    fun iniciarSesion(
        correo: String,
        password: String
    ) {

        if (correo.isBlank()) {
            _mensaje.value = "El correo es obligatorio"
            return
        }

        if (password.isBlank()) {
            _mensaje.value = "La contraseña es obligatoria"
            return
        }

        viewModelScope.launch {

            _cargando.value = true
            _mensaje.value = ""

            val resultado = repository.iniciarSesion(
                correo,
                password
            )

            resultado.onSuccess { usuario ->

                sessionManager.guardarSesion(
                    correo = usuario.correo,
                    rol = usuario.rol
                )

                _loginExitoso.value = true

                _mensaje.value =
                    "Inicio de sesión exitoso"
            }

            resultado.onFailure {

                _loginExitoso.value = false

                _mensaje.value =
                    it.message ?: "Error al iniciar sesión"
            }

            _cargando.value = false
        }
    }

    fun limpiarLoginExitoso() {
        _loginExitoso.value = false
    }
}