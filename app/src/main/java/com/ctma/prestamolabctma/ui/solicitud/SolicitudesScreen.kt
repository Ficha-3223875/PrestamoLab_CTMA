package com.ctma.prestamolabctma.ui.solicitud

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ctma.prestamolabctma.model.Solicitud

@Composable
fun SolicitudesScreen(
    solicitudes: List<Solicitud>,
    onVolverClick: () -> Unit,
    onCambiarEstado: (Int, String) -> Unit,
    onCancelarSolicitud: (Int) -> Unit
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
            text = "Gestión de solicitudes de préstamo",
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
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(
                    bottom = 16.dp
                )
            ) {

                items(solicitudes) { solicitud ->

                    SolicitudCard(
                        solicitud = solicitud,
                        onCambiarEstado = onCambiarEstado,
                        onCancelarSolicitud = onCancelarSolicitud
                    )
                }
            }
        }

        Button(
            onClick = onVolverClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Volver"
            )
        }
    }
}

@Composable
fun SolicitudCard(
    solicitud: Solicitud,
    onCambiarEstado: (Int, String) -> Unit,
    onCancelarSolicitud: (Int) -> Unit
) {

    var mostrarDialogoCancelar by remember {
        mutableStateOf(false)
    }

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

            // Acciones para solicitudes pendientes
            if (
                solicitud.estado.equals(
                    "Pendiente",
                    ignoreCase = true
                )
            ) {

                Button(
                    onClick = {
                        onCambiarEstado(
                            solicitud.id,
                            "Aprobada"
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Aprobar"
                    )
                }

                Button(
                    onClick = {
                        onCambiarEstado(
                            solicitud.id,
                            "Rechazada"
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Rechazar"
                    )
                }

                Button(
                    onClick = {
                        mostrarDialogoCancelar = true
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Cancelar solicitud"
                    )
                }
            }

            // Mensaje para solicitudes aprobadas
            if (
                solicitud.estado.equals(
                    "Aprobada",
                    ignoreCase = true
                )
            ) {

                Text(
                    text = "No puedes cancelar una solicitud que ya fue aprobada.",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }

    // Diálogo de confirmación
    if (mostrarDialogoCancelar) {

        AlertDialog(
            onDismissRequest = {
                mostrarDialogoCancelar = false
            },
            title = {
                Text(
                    text = "Cancelar solicitud"
                )
            },
            text = {
                Text(
                    text = "¿Estás seguro de que deseas cancelar esta solicitud?"
                )
            },
            confirmButton = {

                TextButton(
                    onClick = {
                        onCancelarSolicitud(solicitud.id)
                        mostrarDialogoCancelar = false
                    }
                ) {
                    Text(
                        text = "Confirmar"
                    )
                }
            },
            dismissButton = {

                TextButton(
                    onClick = {
                        mostrarDialogoCancelar = false
                    }
                ) {
                    Text(
                        text = "Cancelar"
                    )
                }
            }
        )
    }
}