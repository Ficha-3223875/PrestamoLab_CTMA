package com.ctma.prestamolabctma.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ctma.prestamolabctma.data.repository.EstudianteRepository

class EstudianteViewModelFactory(
    private val repository: EstudianteRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (modelClass.isAssignableFrom(EstudianteViewModel::class.java)) {
            return EstudianteViewModel(repository) as T
        }

        throw IllegalArgumentException(
            "ViewModel desconocido: ${modelClass.name}"
        )
    }
}