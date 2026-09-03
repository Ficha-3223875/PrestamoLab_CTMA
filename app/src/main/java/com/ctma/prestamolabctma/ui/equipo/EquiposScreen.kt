package com.ctma.prestamolabctma.ui.equipo

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
import com.ctma.prestamolabctma.model.Equipo

@Composable
fun EquiposScreen(
    equipos: List<Equipo>
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
                        equipo = equipo
                    )
                }
            }
        }
    }
}

@Composable
fun EquipoCard(
    equipo: Equipo
) {

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
                text = if (equipo.disponible) {
                    "Estado: Disponible"
                } else {
                    "Estado: No disponible"
                },
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}