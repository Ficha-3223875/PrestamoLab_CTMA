package com.ctma.prestamolabctma.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "PrestamoLab CTMA",
            style = MaterialTheme.typography.headlineLarge
        )

        Text(
            text = "Sistema de préstamo de equipos",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        Button(
            onClick = onCatalogoClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Catálogo"
            )
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Button(
            onClick = onEquiposClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Equipos"
            )
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Button(
            onClick = onSolicitudesClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Solicitudes"
            )
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Button(
            onClick = onPrestamosClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Mis préstamos"
            )
        }
    }
}