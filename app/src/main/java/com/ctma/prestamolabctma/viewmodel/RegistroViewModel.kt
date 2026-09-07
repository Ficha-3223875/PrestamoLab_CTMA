package com.ctma.prestamolabctma.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ctma.prestamolabctma.data.repository.UsuarioRepository
import com.ctma.prestamolabctma.model.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegistroViewModel(
    private val repository: UsuarioRepository
) : ViewModel() {

    private val _mensaje = MutableStateFlow("")
    val mensaje: StateFlow<String> = _mensaje

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando

    fun registrarUsuario(usuario: Usuario) {

        if (usuario.documento.isBlank()) {
            _mensaje.value = "El documento es obligatorio"
            return
        }

        if (usuario.nombre.isBlank()) {
            _mensaje.value = "El nombre es obligatorio"
            return
        }

        if (usuario.correo.isBlank()) {
            _mensaje.value = "El correo es obligatorio"
            return
        }

        if (!usuario.correo.endsWith("@sena.edu.co", ignoreCase = true)) {
            _mensaje.value =
                "Debes utilizar un correo institucional del SENA"
            return
        }

        if (usuario.password.isBlank()) {
            _mensaje.value = "La contraseña es obligatoria"
            return
        }

        if (usuario.programa.isBlank()) {
            _mensaje.value =
                "El programa de formación es obligatorio"
            return
        }

        if (usuario.ficha.isBlank()) {
            _mensaje.value = "La ficha es obligatoria"
            return
        }

        viewModelScope.launch {

            _cargando.value = true
            _mensaje.value = ""

            val resultado = repository.registrarUsuario(usuario)

            resultado
                .onSuccess {
                    _mensaje.value =
                        "Usuario registrado correctamente"
                }
                .onFailure { error ->

                    _mensaje.value =
                        error.message
                            ?: "No se pudo registrar el usuario"
                }

            _cargando.value = false
        }
    }

    fun limpiarMensaje() {
        _mensaje.value = ""
    }
}