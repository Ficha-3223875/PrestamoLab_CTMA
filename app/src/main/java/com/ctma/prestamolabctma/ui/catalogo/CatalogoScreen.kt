package com.ctma.prestamolabctma.ui.catalogo

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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Block
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
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
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp)
    ) {

        Text(
            text = "Catálogo",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Equipos disponibles para préstamo",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 4.dp, bottom = 18.dp)
        )

        if (equipos.isEmpty()) {

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = "No hay equipos registrados.",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(top = 12.dp)
                    )

                    Text(
                        text = "Cuando existan equipos disponibles aparecerán aquí.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

        } else {

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 20.dp)
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
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Devices,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 12.dp)
                ) {
                    Text(
                        text = equipo.nombre,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = equipo.tipo,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(50.dp),
                color = if (equipo.disponible) {
                    MaterialTheme.colorScheme.secondaryContainer
                } else {
                    MaterialTheme.colorScheme.errorContainer
                }
            ) {
                Row(
                    modifier = Modifier.padding(
                        horizontal = 12.dp,
                        vertical = 7.dp
                    )
                ) {
                    Icon(
                        imageVector = if (equipo.disponible) {
                            Icons.Default.CheckCircle
                        } else {
                            Icons.Default.Block
                        },
                        contentDescription = null,
                        modifier = Modifier.padding(end = 6.dp)
                    )

                    Text(
                        text = if (equipo.disponible) {
                            "Disponible"
                        } else {
                            "No disponible"
                        },
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }

            Button(
                onClick = {
                    onEquipoClick(equipo)
                },
                enabled = equipo.disponible,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp)
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