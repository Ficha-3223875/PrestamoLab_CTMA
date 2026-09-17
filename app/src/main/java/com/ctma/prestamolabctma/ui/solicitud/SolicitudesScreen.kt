package com.ctma.prestamolabctma.ui.solicitud

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
    onCancelarSolicitud: (Int) -> Unit,
    onRechazarSolicitud: (Int, String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp)
    ) {

        Text(
            text = "Solicitudes",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Gestiona las solicitudes de préstamo.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 4.dp, bottom = 18.dp)
        )

        if (solicitudes.isEmpty()) {

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Assignment,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = "No hay solicitudes",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                }
            }

        } else {

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {

                items(
                    solicitudes.sortedBy {
                        it.fechaPrestamo
                    }
                ) { solicitud ->

                    SolicitudCard(
                        solicitud = solicitud,
                        onCambiarEstado = onCambiarEstado,
                        onCancelarSolicitud = onCancelarSolicitud
                    )
                }
            }
        }

        FilledTonalButton(
            onClick = onVolverClick,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Volver")
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

    var mostrarDialogoRechazo by remember {
        mutableStateOf(false)
    }

    var motivoRechazo by remember {
        mutableStateOf("")
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {

            Text(
                text = solicitud.equipo.nombre,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = solicitud.equipo.tipo,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = "Préstamo: ${solicitud.fechaPrestamo}"
            )

            Text(
                text = "Devolución: ${solicitud.fechaDevolucion}"
            )

            Text(
                text = "Motivo: ${solicitud.motivo}"
            )

            Text(
                text = "Estado: ${solicitud.estado}",
                style = MaterialTheme.typography.titleSmall
            )

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
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                ) {

                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null
                    )

                    Text(
                        "Aprobar",
                        modifier = Modifier.padding(start = 6.dp)
                    )
                }

                FilledTonalButton(
                    onClick = {
                        mostrarDialogoRechazo = true
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null
                    )

                    Text(
                        "Rechazar",
                        modifier = Modifier.padding(start = 6.dp)
                    )
                }

                TextButton(
                    onClick = {
                        mostrarDialogoCancelar = true
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cancelar solicitud")
                }
            }

            if (
                solicitud.estado.equals(
                    "Aprobada",
                    ignoreCase = true
                )
            ) {

                Button(
                    onClick = {
                        onCambiarEstado(
                            solicitud.id,
                            "En Préstamo"
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                ) {

                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = null
                    )

                    Text(
                        "Entregar equipo",
                        modifier = Modifier.padding(start = 6.dp)
                    )
                }

                Text(
                    text = "La solicitud fue aprobada y está lista para entrega.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }

    if (mostrarDialogoCancelar) {

        AlertDialog(
            onDismissRequest = {
                mostrarDialogoCancelar = false
            },
            title = {
                Text("Cancelar solicitud")
            },
            text = {
                Text(
                    "¿Estás seguro de que deseas cancelar esta solicitud?"
                )
            },
            confirmButton = {

                TextButton(
                    onClick = {
                        onCancelarSolicitud(solicitud.id)
                        mostrarDialogoCancelar = false
                    }
                ) {
                    Text("Confirmar")
                }
            },
            dismissButton = {

                TextButton(
                    onClick = {
                        mostrarDialogoCancelar = false
                    }
                ) {
                    Text("Volver")
                }
            }
        )
    }

    if (mostrarDialogoRechazo) {

        AlertDialog(
            onDismissRequest = {
                mostrarDialogoRechazo = false
            },
            title = {
                Text("Rechazar solicitud")
            },
            text = {

                OutlinedTextField(
                    value = motivoRechazo,
                    onValueChange = {
                        motivoRechazo = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Motivo del rechazo")
                    },
                    minLines = 3
                )
            },
            confirmButton = {

                TextButton(
                    onClick = {

                        if (motivoRechazo.isNotBlank()) {

                            onCambiarEstado(
                                solicitud.id,
                                "Rechazada"
                            )

                            mostrarDialogoRechazo = false
                            motivoRechazo = ""
                        }
                    }
                ) {
                    Text("Rechazar")
                }
            },
            dismissButton = {

                TextButton(
                    onClick = {
                        mostrarDialogoRechazo = false
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}