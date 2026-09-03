package com.ctma.prestamolabctma.ui.catalogo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import com.ctma.prestamolabctma.model.Equipo


@Composable
fun CatalogoScreen(
    equipos: List<Equipo>,
    onEquipoClick: (Equipo) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = "Catálogo de equipos",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Selecciona un equipo para ver sus detalles",
            modifier = Modifier.padding(
                top = 8.dp,
                bottom = 16.dp
            )
        )

        if (equipos.isEmpty()) {

            Text(
                text = "No hay equipos disponibles."
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

                    EquipoCatalogoCard(
                        equipo = equipo,
                        onEquipoClick = onEquipoClick
                    )
                }
            }
        }
    }
}


@Composable
fun EquipoCatalogoCard(
    equipo: Equipo,
    onEquipoClick: (Equipo) -> Unit
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
                }
            )

            Button(
                onClick = {
                    onEquipoClick(equipo)
                },
                enabled = equipo.disponible,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (equipo.disponible) {
                        "Ver detalles"
                    } else {
                        "No disponible"
                    }
                )
            }
        }
    }
}