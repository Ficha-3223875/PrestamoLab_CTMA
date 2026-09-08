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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ctma.prestamolabctma.model.Solicitud

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

            if (solicitud.estado.equals("Aprobada", ignoreCase = true)) {

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

        estado.equals("Devuelto", ignoreCase = true) ->
            "🔵 Devuelto"

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