package com.ctma.prestamolabctma.data.repository

import com.ctma.prestamolabctma.data.local.dao.EquipoDao
import com.ctma.prestamolabctma.data.local.entity.EquipoEntity
import com.ctma.prestamolabctma.model.Equipo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EquipoRepository(
    private val equipoDao: EquipoDao
) {

    fun obtenerEquipos(): Flow<List<Equipo>> {

        return equipoDao
            .obtenerEquipos()
            .map { equipos ->

                equipos.map { equipo ->

                    Equipo(
                        id = equipo.id,
                        nombre = equipo.nombre,
                        tipo = equipo.tipo,
                        codigo = equipo.codigo,
                        disponible = equipo.disponible,
                        estado = equipo.estado
                    )
                }
            }
    }

    suspend fun guardarEquipo(equipo: Equipo) {

        equipoDao.insertarEquipo(
            EquipoEntity(
                id = equipo.id,
                nombre = equipo.nombre,
                tipo = equipo.tipo,
                codigo = equipo.codigo,
                disponible = equipo.disponible,
                estado = equipo.estado
            )
        )
    }

    suspend fun guardarEquipos(equipos: List<Equipo>) {

        equipoDao.insertarEquipos(
            equipos.map { equipo ->

                EquipoEntity(
                    id = equipo.id,
                    nombre = equipo.nombre,
                    tipo = equipo.tipo,
                    codigo = equipo.codigo,
                    disponible = equipo.disponible,
                    estado = equipo.estado
                )
            }
        )
    }

    suspend fun actualizarEquipo(equipo: Equipo) {

        equipoDao.actualizarEquipo(
            EquipoEntity(
                id = equipo.id,
                nombre = equipo.nombre,
                tipo = equipo.tipo,
                codigo = equipo.codigo,
                disponible = equipo.disponible,
                estado = equipo.estado
            )
        )
    }

    suspend fun eliminarEquipo(equipo: Equipo) {

        equipoDao.eliminarEquipo(
            EquipoEntity(
                id = equipo.id,
                nombre = equipo.nombre,
                tipo = equipo.tipo,
                codigo = equipo.codigo,
                disponible = equipo.disponible,
                estado = equipo.estado
            )
        )
    }
}