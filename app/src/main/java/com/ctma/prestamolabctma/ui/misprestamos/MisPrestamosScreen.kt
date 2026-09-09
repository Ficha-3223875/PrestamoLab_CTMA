package com.ctma.prestamolabctma.ui.misprestamos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
            .padding(24.dp)
    ) {

        Text(
            text = "Historial de préstamos",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Consulta tus préstamos activos y pasados.",
            modifier = Modifier.padding(
                top = 8.dp,
                bottom = 16.dp
            )
        )

        Button(
            onClick = onVolverClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Volver al inicio"
            )
        }

        if (solicitudes.isEmpty()) {

            Text(
                text = "No tienes préstamos registrados.",
                modifier = Modifier.padding(top = 16.dp)
            )

        } else {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(
                    bottom = 16.dp
                )
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

            EstadoPrestamo(
                estado = solicitud.estado
            )

            // Contador solamente cuando el equipo está en préstamo
            if (solicitud.estado.equals("En Préstamo", ignoreCase = true)) {

                ContadorDevolucion(
                    fechaDevolucion = solicitud.fechaDevolucion
                )

                Button(
                    onClick = {
                        onDevolverClick(solicitud)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Devolver préstamo"
                    )
                }
            }
        }
    }
}

@Composable
fun EstadoPrestamo(
    estado: String
) {

    val indicador = when {

        estado.equals("Aprobada", ignoreCase = true) ->
            "🟢 Aprobado"

        estado.equals("Pendiente", ignoreCase = true) ->
            "🟡 Pendiente"

        estado.equals("En Préstamo", ignoreCase = true) ->
            "🟠 En Préstamo"

        estado.equals("Devuelto", ignoreCase = true) ->
            "🔵 Devuelto"

        estado.equals("Cancelada", ignoreCase = true) ->
            "⚪ Cancelada"

        estado.equals("Rechazada", ignoreCase = true) ->
            "🔴 Rechazada"

        else ->
            "⚪ $estado"
    }

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = "Estado: $indicador",
            style = MaterialTheme.typography.titleSmall
        )
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
                            "⚠️ Fecha de devolución cumplida"

                    } else {

                        val horas =
                            diferencia / (1000 * 60 * 60)

                        val minutos =
                            (diferencia / (1000 * 60)) % 60

                        tiempoRestante =
                            "⏱️ Tiempo restante: ${horas}h ${minutos}min"
                    }
                }

            } catch (e: Exception) {

                tiempoRestante =
                    "No se pudo calcular el tiempo restante"
            }

            delay(60_000)
        }
    }

    Text(
        text = tiempoRestante,
        style = MaterialTheme.typography.bodyMedium
    )
}