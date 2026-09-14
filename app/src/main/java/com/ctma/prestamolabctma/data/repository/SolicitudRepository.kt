package com.ctma.prestamolabctma.data.repository

import com.ctma.prestamolabctma.data.local.entity.dao.EquipoDao
import com.ctma.prestamolabctma.data.local.entity.dao.SolicitudDao
import com.ctma.prestamolabctma.data.local.entity.SolicitudEntity
import com.ctma.prestamolabctma.model.Equipo
import com.ctma.prestamolabctma.model.Solicitud
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class SolicitudRepository(
    private val solicitudDao: SolicitudDao,
    private val equipoDao: EquipoDao
) {

    fun obtenerSolicitudes(): Flow<List<Solicitud>> {

        return combine(
            solicitudDao.obtenerSolicitudes(),
            equipoDao.obtenerEquipos()
        ) { solicitudes, equipos ->

            solicitudes.mapNotNull { solicitud ->

                val equipo =
                    equipos.find {
                        it.id == solicitud.equipoId
                    }

                if (equipo != null) {

                    Solicitud(
                        id = solicitud.id,

                        equipo = Equipo(
                            id = equipo.id,
                            nombre = equipo.nombre,
                            tipo = equipo.tipo,
                            codigo = equipo.codigo,
                            disponible = equipo.disponible,
                            estado = equipo.estado
                        ),

                        fechaPrestamo =
                            solicitud.fechaPrestamo,

                        fechaDevolucion =
                            solicitud.fechaDevolucion,

                        motivo =
                            solicitud.motivo,

                        estado =
                            solicitud.estado,

                        motivoRechazo =
                            solicitud.motivoRechazo
                    )

                } else {
                    null
                }
            }
        }
    }

    suspend fun guardarSolicitud(
        solicitud: Solicitud
    ) {

        solicitudDao.insertarSolicitud(

            SolicitudEntity(
                id = solicitud.id,
                equipoId = solicitud.equipo.id,
                fechaPrestamo =
                    solicitud.fechaPrestamo,
                fechaDevolucion =
                    solicitud.fechaDevolucion,
                motivo =
                    solicitud.motivo,
                estado =
                    solicitud.estado,
                motivoRechazo =
                    solicitud.motivoRechazo
            )
        )
    }

    suspend fun actualizarSolicitud(
        solicitud: Solicitud
    ) {

        solicitudDao.actualizarSolicitud(

            SolicitudEntity(
                id = solicitud.id,
                equipoId = solicitud.equipo.id,
                fechaPrestamo =
                    solicitud.fechaPrestamo,
                fechaDevolucion =
                    solicitud.fechaDevolucion,
                motivo =
                    solicitud.motivo,
                estado =
                    solicitud.estado,
                motivoRechazo =
                    solicitud.motivoRechazo
            )
        )
    }

    suspend fun eliminarSolicitud(
        solicitud: Solicitud
    ) {

        solicitudDao.eliminarSolicitud(

            SolicitudEntity(
                id = solicitud.id,
                equipoId = solicitud.equipo.id,
                fechaPrestamo =
                    solicitud.fechaPrestamo,
                fechaDevolucion =
                    solicitud.fechaDevolucion,
                motivo =
                    solicitud.motivo,
                estado =
                    solicitud.estado,
                motivoRechazo =
                    solicitud.motivoRechazo
            )
        )
    }
}