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
fun EquiposScreen() {

    val equipos = listOf(
        Equipo(
            id = 1,
            nombre = "Portátil Lenovo",
            tipo = "Computador",
            disponible = true
        ),
        Equipo(
            id = 2,
            nombre = "Proyector Epson",
            tipo = "Proyector",
            disponible = true
        ),
        Equipo(
            id = 3,
            nombre = "Cámara Canon",
            tipo = "Cámara",
            disponible = false
        ),
        Equipo(
            id = 4,
            nombre = "Tablet Samsung",
            tipo = "Tablet",
            disponible = true
        )
    )

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
            modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {

            items(equipos) { equipo ->

                EquipoCard(equipo = equipo)
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
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = equipo.nombre,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Tipo: ${equipo.tipo}",
                modifier = Modifier.padding(top = 6.dp)
            )

            Text(
                text = if (equipo.disponible) {
                    "Disponible"
                } else {
                    "No disponible"
                },
                modifier = Modifier.padding(top = 6.dp)
            )
        }
    }
}