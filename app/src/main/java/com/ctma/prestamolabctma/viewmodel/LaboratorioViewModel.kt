package com.ctma.prestamolabctma.viewmodel

import androidx.lifecycle.ViewModel
import com.ctma.prestamolabctma.model.Laboratorio
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LaboratorioViewModel : ViewModel() {

    private val _laboratorios =
        MutableStateFlow(
            listOf(
                Laboratorio(
                    id = 1,
                    nombre = "Laboratorio de Sistemas",
                    codigo = "LAB-001"
                ),
                Laboratorio(
                    id = 2,
                    nombre = "Laboratorio de Electrónica",
                    codigo = "LAB-002"
                )
            )
        )

    val laboratorios: StateFlow<List<Laboratorio>> =
        _laboratorios

    fun agregarLaboratorio(
        nombre: String,
        codigo: String
    ): Boolean {

        if (nombre.isBlank() || codigo.isBlank()) {
            return false
        }

        val codigoExiste =
            _laboratorios.value.any {
                it.codigo.equals(
                    codigo.trim(),
                    ignoreCase = true
                )
            }

        if (codigoExiste) {
            return false
        }

        val nuevoId =
            (_laboratorios.value.maxOfOrNull {
                it.id
            } ?: 0) + 1

        val laboratorio = Laboratorio(
            id = nuevoId,
            nombre = nombre.trim(),
            codigo = codigo.trim()
        )

        _laboratorios.value =
            _laboratorios.value + laboratorio

        return true
    }

    fun actualizarLaboratorio(
        id: Int,
        nombre: String,
        codigo: String,
        estado: String
    ): Boolean {

        if (nombre.isBlank() || codigo.isBlank()) {
            return false
        }

        val codigoExiste =
            _laboratorios.value.any {
                it.id != id &&
                        it.codigo.equals(
                            codigo.trim(),
                            ignoreCase = true
                        )
            }

        if (codigoExiste) {
            return false
        }

        _laboratorios.value =
            _laboratorios.value.map { laboratorio ->

                if (laboratorio.id == id) {

                    laboratorio.copy(
                        nombre = nombre.trim(),
                        codigo = codigo.trim(),
                        estado = estado
                    )

                } else {

                    laboratorio
                }
            }

        return true
    }

    fun eliminarLaboratorio(
        id: Int
    ) {

        _laboratorios.value =
            _laboratorios.value.filter {
                it.id != id
            }
    }

    fun cambiarEstado(
        id: Int,
        estado: String
    ) {

        _laboratorios.value =
            _laboratorios.value.map { laboratorio ->

                if (laboratorio.id == id) {

                    laboratorio.copy(
                        estado = estado
                    )

                } else {

                    laboratorio
                }
            }
    }
}