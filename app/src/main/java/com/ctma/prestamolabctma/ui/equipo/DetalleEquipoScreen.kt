package com.ctma.prestamolabctma.ui.equipo

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
import com.ctma.prestamolabctma.model.Equipo

@Composable
fun DetalleEquipoScreen(
    equipo: Equipo,
    onSolicitarClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Detalle del equipo"
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "Nombre: ${equipo.nombre}"
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Tipo: ${equipo.tipo}"
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = if (equipo.disponible) {
                "Estado: Disponible"
            } else {
                "Estado: No disponible"
            }
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Button(
            onClick = onSolicitarClick,
            enabled = equipo.disponible,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Solicitar préstamo")
        }
    }
}