package com.ctma.prestamolabctma.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ctma.prestamolabctma.data.local.AppDatabase
import com.ctma.prestamolabctma.data.repository.EquipoRepository
import com.ctma.prestamolabctma.model.Equipo
import com.ctma.prestamolabctma.model.Incidente
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EquipoViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val equipoRepository: EquipoRepository

    private val _equipos =
        MutableStateFlow<List<Equipo>>(emptyList())

    private val _incidentes =
        MutableStateFlow<List<Incidente>>(emptyList())

    val equipos: StateFlow<List<Equipo>> =
        _equipos

    val incidentes: StateFlow<List<Incidente>> =
        _incidentes

    private val equiposIniciales =
        listOf(
            Equipo(
                id = 1,
                nombre = "Laptop Lenovo",
                tipo = "Computador",
                codigo = "EQ-001",
                disponible = true
            ),
            Equipo(
                id = 2,
                nombre = "Proyector Epson",
                tipo = "Proyector",
                codigo = "EQ-002",
                disponible = true
            ),
            Equipo(
                id = 3,
                nombre = "Cámara Sony",
                tipo = "Cámara",
                codigo = "EQ-003",
                disponible = true
            ),
            Equipo(
                id = 4,
                nombre = "Tablet Samsung",
                tipo = "Tablet",
                codigo = "EQ-004",
                disponible = true
            )
        )

    init {

        val database =
            AppDatabase.getDatabase(application)

        equipoRepository =
            EquipoRepository(
                database.equipoDao()
            )

        cargarEquipos()
    }

    // =====================================================
    // CARGAR EQUIPOS DESDE ROOM
    // =====================================================

    private fun cargarEquipos() {

        viewModelScope.launch {

            val equiposGuardados =
                equipoRepository
                    .obtenerEquipos()
                    .first()

            if (equiposGuardados.isEmpty()) {

                equipoRepository.guardarEquipos(
                    equiposIniciales
                )
            }

            equipoRepository
                .obtenerEquipos()
                .collect { equipos ->

                    _equipos.value = equipos
                }
        }
    }

    // =====================================================
    // AGREGAR EQUIPO
    // =====================================================

    fun agregarEquipo(
        nombre: String,
        tipo: String,
        codigo: String
    ): Boolean {

        if (
            nombre.isBlank() ||
            tipo.isBlank() ||
            codigo.isBlank()
        ) {
            return false
        }

        val codigoLimpio =
            codigo.trim()

        val codigoExiste =
            _equipos.value.any {

                it.codigo.equals(
                    codigoLimpio,
                    ignoreCase = true
                )
            }

        if (codigoExiste) {
            return false
        }

        val nuevoId =
            (_equipos.value.maxOfOrNull {
                it.id
            } ?: 0) + 1

        val nuevoEquipo =
            Equipo(
                id = nuevoId,
                nombre = nombre.trim(),
                tipo = tipo.trim(),
                codigo = codigoLimpio,
                disponible = true,
                estado = "Disponible"
            )

        viewModelScope.launch {

            equipoRepository.guardarEquipo(
                nuevoEquipo
            )
        }

        return true
    }

    // =====================================================
    // EDITAR EQUIPO
    // =====================================================

    fun actualizarEquipo(
        id: Int,
        nombre: String,
        tipo: String,
        codigo: String,
        estado: String,
        disponible: Boolean
    ): Boolean {

        if (
            nombre.isBlank() ||
            tipo.isBlank() ||
            codigo.isBlank()
        ) {
            return false
        }

        val codigoLimpio =
            codigo.trim()

        val codigoExiste =
            _equipos.value.any {

                it.id != id &&
                        it.codigo.equals(
                            codigoLimpio,
                            ignoreCase = true
                        )
            }

        if (codigoExiste) {
            return false
        }

        val equipo =
            _equipos.value.find {
                it.id == id
            }

        if (equipo == null) {
            return false
        }

        val equipoActualizado =
            equipo.copy(
                nombre = nombre.trim(),
                tipo = tipo.trim(),
                codigo = codigoLimpio,
                estado = estado,
                disponible = disponible
            )

        viewModelScope.launch {

            equipoRepository.actualizarEquipo(
                equipoActualizado
            )
        }

        return true
    }

    // =====================================================
    // ELIMINAR EQUIPO
    // =====================================================

    fun eliminarEquipo(
        id: Int
    ) {

        val equipo =
            _equipos.value.find {
                it.id == id
            }

        if (equipo == null) {
            return
        }

        viewModelScope.launch {

            equipoRepository.eliminarEquipo(
                equipo
            )
        }
    }

    // =====================================================
    // CAMBIAR DISPONIBILIDAD
    // =====================================================

    fun actualizarDisponibilidad(
        idEquipo: Int,
        disponible: Boolean
    ) {

        val equipo =
            _equipos.value.find {
                it.id == idEquipo
            }

        if (equipo == null) {
            return
        }

        val equipoActualizado =
            equipo.copy(
                disponible = disponible,
                estado = if (disponible) {
                    "Disponible"
                } else {
                    "No disponible"
                }
            )

        viewModelScope.launch {

            equipoRepository.actualizarEquipo(
                equipoActualizado
            )
        }
    }

    // =====================================================
    // REPORTAR INCIDENTE
    // =====================================================

    fun reportarIncidente(
        equipoId: Int,
        observacion: String
    ) {

        val equipo =
            _equipos.value.find {
                it.id == equipoId
            }

        if (
            equipo != null &&
            observacion.isNotBlank()
        ) {

            val incidente =
                Incidente(
                    id = System.currentTimeMillis().toInt(),
                    equipoId = equipo.id,
                    equipoNombre = equipo.nombre,
                    observacion = observacion.trim(),
                    fecha =
                        java.text.SimpleDateFormat(
                            "yyyy-MM-dd HH:mm",
                            java.util.Locale.getDefault()
                        ).format(
                            java.util.Date()
                        )
                )

            _incidentes.value =
                _incidentes.value + incidente

            val equipoActualizado =
                equipo.copy(
                    disponible = false,
                    estado = "En Mantenimiento"
                )

            viewModelScope.launch {

                equipoRepository.actualizarEquipo(
                    equipoActualizado
                )
            }
        }
    }

    // =====================================================
    // FINALIZAR MANTENIMIENTO
    // =====================================================

    fun finalizarMantenimiento(
        equipoId: Int
    ) {

        val equipo =
            _equipos.value.find {
                it.id == equipoId
            }

        if (equipo == null) {
            return
        }

        val equipoActualizado =
            equipo.copy(
                disponible = true,
                estado = "Disponible"
            )

        viewModelScope.launch {

            equipoRepository.actualizarEquipo(
                equipoActualizado
            )
        }
    }
}