package com.ctma.prestamolabctma.ui.solicitud

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ctma.prestamolabctma.model.Solicitud

@Composable
fun SolicitudesScreen(
    solicitudes: List<Solicitud>,
    onCambiarEstado: (Int, String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = "Solicitudes",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Solicitudes de préstamo",
            modifier = Modifier.padding(
                top = 8.dp,
                bottom = 16.dp
            )
        )

        if (solicitudes.isEmpty()) {

            Text(
                text = "No hay solicitudes registradas."
            )

        } else {

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(solicitudes) { solicitud ->

                    SolicitudCard(
                        solicitud = solicitud,
                        onAprobar = {
                            onCambiarEstado(
                                solicitud.id,
                                "Aprobada"
                            )
                        },
                        onRechazar = {
                            onCambiarEstado(
                                solicitud.id,
                                "Rechazada"
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SolicitudCard(
    solicitud: Solicitud,
    onAprobar: () -> Unit,
    onRechazar: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                text = solicitud.equipo.nombre,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Tipo: ${solicitud.equipo.tipo}"
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

            if (solicitud.estado.equals("Pendiente", ignoreCase = true)) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Button(
                        onClick = onAprobar,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Aprobar")
                    }

                    Button(
                        onClick = onRechazar,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Rechazar")
                    }
                }
            }
        }
    }
}