package com.ctma.prestamolabctma.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
            text = "PrestamoLab CTMA"
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Bienvenido"
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Button(
            onClick = onCatalogoClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Catálogo")
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Button(
            onClick = onEquiposClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Equipos")
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Button(
            onClick = onPrestamosClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Mis préstamos")
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Button(
            onClick = onSolicitudesClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Solicitudes")
        }
    }
}