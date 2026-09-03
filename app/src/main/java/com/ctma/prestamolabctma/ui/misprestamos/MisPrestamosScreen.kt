package com.ctma.prestamolabctma.ui.misprestamos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ctma.prestamolabctma.model.Solicitud

@Composable
fun MisPrestamosScreen(
    solicitudes: List<Solicitud>
) {

    val prestamosAprobados = solicitudes.filter {
        it.estado.equals("Aprobada", ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = "Mis préstamos",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Préstamos aprobados",
            modifier = Modifier.padding(
                top = 8.dp,
                bottom = 16.dp
            )
        )

        if (prestamosAprobados.isEmpty()) {

            Text(
                text = "No tienes préstamos aprobados."
            )

        } else {

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {

                items(prestamosAprobados) { solicitud ->

                    PrestamoCard(
                        solicitud = solicitud
                    )
                }
            }
        }
    }
}

@Composable
fun PrestamoCard(
    solicitud: Solicitud
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
        }
    }
}