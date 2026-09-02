package com.ctma.prestamolabctma.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ctma.prestamolabctma.data.repository.EstudianteRepository
import com.ctma.prestamolabctma.model.Estudiante
import kotlinx.coroutines.launch

class EstudianteViewModel(
    private val repository: EstudianteRepository
) : ViewModel() {

    fun registrarEstudiante(estudiante: Estudiante) {
        viewModelScope.launch {
            try {
                val respuesta = repository.registrarEstudiante(estudiante)

                if (respuesta.isSuccessful) {
                    println("Estudiante registrado correctamente")
                } else {
                    println("Error al registrar estudiante: ${respuesta.code()}")
                }

            } catch (e: Exception) {
                println("Error de conexión: ${e.message}")
            }
        }
    }
}