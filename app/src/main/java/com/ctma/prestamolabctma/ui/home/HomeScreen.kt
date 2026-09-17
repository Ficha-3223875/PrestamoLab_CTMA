package com.ctma.prestamolabctma.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onCatalogoClick: () -> Unit,
    onEquiposClick: () -> Unit,
    onPrestamosClick: () -> Unit,
    onSolicitudesClick: () -> Unit,
    onLaboratoriosClick: () -> Unit,
    onMetricasClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp)
    ) {

        Text(
            text = "PréstamoLab",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "CTMA",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Gestión de préstamos de equipos",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Column(
                modifier = Modifier.padding(22.dp)
            ) {
                Text(
                    text = "Bienvenido",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Consulta equipos, realiza solicitudes y administra tus préstamos.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Accesos rápidos",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            HomeCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.Inventory,
                title = "Catálogo",
                onClick = onCatalogoClick
            )

            HomeCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.Devices,
                title = "Equipos",
                onClick = onEquiposClick
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            HomeCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.Assignment,
                title = "Solicitudes",
                onClick = onSolicitudesClick
            )

            HomeCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.ListAlt,
                title = "Préstamos",
                onClick = onPrestamosClick
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            HomeCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.Business,
                title = "Laboratorios",
                onClick = onLaboratoriosClick
            )

            HomeCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.BarChart,
                title = "Métricas",
                onClick = onMetricasClick
            )
        }
    }
}

@Composable
private fun HomeCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}