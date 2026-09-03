package com.ctma.prestamolabctma.ui.misprestamos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ctma.prestamolabctma.model.Solicitud

@Composable
fun MisPrestamosScreen(
    solicitudes: List<Solicitud>
) {

    val prestamos = solicitudes.filter {
        it.estado == "Aprobada" || it.estado == "Activo"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = "Mis préstamos"
        )

        if (prestamos.isEmpty()) {

            Text(
                text = "No tienes préstamos activos.",
                modifier = Modifier.padding(top = 16.dp)
            )

        } else {

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(prestamos) { solicitud ->

                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            Text(
                                text = "Equipo: ${solicitud.equipo.nombre}"
                            )

                            Text(
                                text = "Fecha de préstamo: ${solicitud.fechaPrestamo}"
                            )

                            Text(
                                text = "Fecha de devolución: ${solicitud.fechaDevolucion}"
                            )

                            Text(
                                text = "Motivo: ${solicitud.motivo}"
                            )

                            Text(
                                text = "Estado: ${solicitud.estado}"
                            )
                        }
                    }
                }
            }
        }
    }
}