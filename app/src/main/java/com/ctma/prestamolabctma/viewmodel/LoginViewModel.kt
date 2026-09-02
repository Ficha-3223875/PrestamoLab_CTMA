package com.ctma.prestamolabctma.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel : ViewModel() {

    private val _mensaje = MutableStateFlow("")
    val mensaje: StateFlow<String> = _mensaje

    fun iniciarSesion(correo: String, password: String) {

        if (correo.isBlank()) {
            _mensaje.value = "El correo es obligatorio"
            return
        }

        if (password.isBlank()) {
            _mensaje.value = "La contraseña es obligatoria"
            return
        }

        _mensaje.value = "Datos de inicio de sesión válidos"
    }
}