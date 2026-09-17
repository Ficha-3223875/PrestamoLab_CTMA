package com.ctma.prestamolabctma.ui.misprestamos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ctma.prestamolabctma.model.Solicitud
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun MisPrestamosScreen(
    solicitudes: List<Solicitud>,
    onVolverClick: () -> Unit,
    onDevolverClick: (Solicitud) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp)
    ) {

        Text(
            text = "Mis préstamos",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Consulta tus préstamos activos y anteriores.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 4.dp, bottom = 18.dp)
        )

        FilledTonalButton(
            onClick = onVolverClick,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Volver al inicio")
        }

        if (solicitudes.isEmpty()) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 18.dp),
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
                        text = "Sin préstamos",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(top = 10.dp)
                    )

                    Text(
                        text = "No tienes préstamos registrados actualmente.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

        } else {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 20.dp)
            ) {

                items(solicitudes) { solicitud ->

                    PrestamoCard(
                        solicitud = solicitud,
                        onDevolverClick = onDevolverClick
                    )
                }
            }
        }
    }
}

@Composable
fun PrestamoCard(
    solicitud: Solicitud,
    onDevolverClick: (Solicitud) -> Unit
) {

    var mostrarDialogoDevolucion by remember {
        mutableStateOf(false)
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

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                Icon(
                    imageVector = Icons.Default.Assignment,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 12.dp)
                ) {

                    Text(
                        text = solicitud.equipo.nombre,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = solicitud.equipo.tipo,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Text(
                text = "Préstamo: ${solicitud.fechaPrestamo}"
            )

            Text(
                text = "Devolución: ${solicitud.fechaDevolucion}"
            )

            Text(
                text = "Motivo: ${solicitud.motivo}"
            )

            EstadoPrestamo(
                estado = solicitud.estado
            )

            if (
                solicitud.estado.equals(
                    "En Préstamo",
                    ignoreCase = true
                )
            ) {

                ContadorDevolucion(
                    fechaDevolucion = solicitud.fechaDevolucion
                )

                Button(
                    onClick = {
                        mostrarDialogoDevolucion = true
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text("Devolver préstamo")
                }
            }
        }
    }

    if (mostrarDialogoDevolucion) {

        AlertDialog(
            onDismissRequest = {
                mostrarDialogoDevolucion = false
            },
            title = {
                Text("Confirmar devolución")
            },
            text = {
                Text(
                    "¿Confirmas que el equipo ${solicitud.equipo.nombre} será marcado como devuelto?"
                )
            },
            confirmButton = {

                TextButton(
                    onClick = {

                        onDevolverClick(solicitud)

                        mostrarDialogoDevolucion = false
                    }
                ) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        mostrarDialogoDevolucion = false
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun EstadoPrestamo(
    estado: String
) {

    val color =
        when {
            estado.equals("Aprobada", true) ||
                    estado.equals("Devuelto", true) ->
                MaterialTheme.colorScheme.secondaryContainer

            estado.equals("En Préstamo", true) ||
                    estado.equals("Pendiente", true) ->
                MaterialTheme.colorScheme.tertiaryContainer

            estado.equals("Rechazada", true) ||
                    estado.equals("Cancelada", true) ->
                MaterialTheme.colorScheme.errorContainer

            else ->
                MaterialTheme.colorScheme.surfaceVariant
        }

    val icon =
        if (
            estado.equals("Aprobada", true) ||
            estado.equals("Devuelto", true)
        ) {
            Icons.Default.CheckCircle
        } else {
            Icons.Default.Timer
        }

    Surface(
        shape = RoundedCornerShape(50.dp),
        color = color
    ) {

        Row(
            modifier = Modifier.padding(
                horizontal = 12.dp,
                vertical = 7.dp
            )
        ) {

            Icon(
                imageVector = icon,
                contentDescription = null
            )

            Text(
                text = estado,
                modifier = Modifier.padding(start = 6.dp)
            )
        }
    }
}

@Composable
fun ContadorDevolucion(
    fechaDevolucion: String
) {

    var tiempoRestante by remember {
        mutableStateOf("")
    }

    LaunchedEffect(fechaDevolucion) {

        while (true) {

            try {

                val formato = SimpleDateFormat(
                    "yyyy-MM-dd HH:mm",
                    Locale.getDefault()
                )

                val fechaDevolucionCompleta =
                    "$fechaDevolucion 12:00"

                val fecha = formato.parse(
                    fechaDevolucionCompleta
                )

                if (fecha != null) {

                    val diferencia =
                        fecha.time - Date().time

                    if (diferencia <= 0) {

                        tiempoRestante =
                            "Fecha de devolución cumplida"

                    } else {

                        val horas =
                            diferencia / (1000 * 60 * 60)

                        val minutos =
                            (diferencia / (1000 * 60)) % 60

                        tiempoRestante =
                            "Tiempo restante: ${horas}h ${minutos}min"
                    }
                }

            } catch (_: Exception) {

                tiempoRestante =
                    "No se pudo calcular el tiempo restante"
            }

            delay(60_000)
        }
    }

    Text(
        text = tiempoRestante,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.primary
    )
}