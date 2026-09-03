package com.ctma.prestamolabctma.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onCatalogoClick: () -> Unit,
    onEquiposClick: () -> Unit,
    onPrestamosClick: () -> Unit,
    onSolicitudesClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "PréstamoLab CTMA",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Sistema de préstamo de equipos",
            style = MaterialTheme.typography.bodyLarge
        )

        Button(
            onClick = onCatalogoClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Catálogo de equipos")
        }

        Button(
            onClick = onEquiposClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Equipos")
        }

        Button(
            onClick = onSolicitudesClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Solicitudes")
        }

        Button(
            onClick = onPrestamosClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Mis préstamos")
        }
    }
}