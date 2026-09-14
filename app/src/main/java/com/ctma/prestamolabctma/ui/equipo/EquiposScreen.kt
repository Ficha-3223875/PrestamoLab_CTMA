package com.ctma.prestamolabctma.ui.equipo

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
import com.ctma.prestamolabctma.model.Equipo

@Composable
fun EquiposScreen(
    equipos: List<Equipo>,
    onReportarIncidente: (Equipo, String) -> Unit,
    onFinalizarMantenimiento: (Equipo) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = "Equipos",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Gestión de equipos",
            modifier = Modifier.padding(
                top = 8.dp,
                bottom = 16.dp
            )
        )

        if (equipos.isEmpty()) {

            Text(
                text = "No hay equipos registrados."
            )

        } else {

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(
                    bottom = 16.dp
                )
            ) {

                items(equipos) { equipo ->

                    EquipoCard(
                        equipo = equipo,
                        onReportarIncidente = onReportarIncidente,
                        onFinalizarMantenimiento = onFinalizarMantenimiento
                    )
                }
            }
        }
    }
}

@Composable
fun EquipoCard(
    equipo: Equipo,
    onReportarIncidente: (Equipo, String) -> Unit,
    onFinalizarMantenimiento: (Equipo) -> Unit
) {

    var mostrarDialogo by remember {
        mutableStateOf(false)
    }

    var observacion by remember {
        mutableStateOf("")
    }

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                text = equipo.nombre,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Tipo: ${equipo.tipo}"
            )

            Text(
                text = "Estado: ${equipo.estado}"
            )

            if (equipo.estado.equals(
                    "En Mantenimiento",
                    ignoreCase = true
                )
            ) {

                Text(
                    text = "⚠️ Este equipo no está disponible para préstamos."
                )

                Button(
                    onClick = {
                        onFinalizarMantenimiento(equipo)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Finalizar mantenimiento")
                }

            } else {

                Button(
                    onClick = {
                        observacion = ""
                        mostrarDialogo = true
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Reportar incidente")
                }
            }
        }
    }

    if (mostrarDialogo) {

        AlertDialog(
            onDismissRequest = {
                mostrarDialogo = false
            },

            title = {
                Text("Reportar incidente")
            },

            text = {

                Column {

                    Text(
                        text = "Equipo: ${equipo.nombre}"
                    )

                    OutlinedTextField(
                        value = observacion,
                        onValueChange = {
                            observacion = it
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp),
                        label = {
                            Text("Observación")
                        },
                        minLines = 3
                    )
                }
            },

            confirmButton = {

                TextButton(
                    onClick = {

                        if (observacion.isNotBlank()) {

                            onReportarIncidente(
                                equipo,
                                observacion.trim()
                            )

                            mostrarDialogo = false
                            observacion = ""
                        }
                    }
                ) {
                    Text("Reportar")
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {
                        mostrarDialogo = false
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}