package com.ctma.prestamolabctma.ui.equipo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.Block
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 5.dp
            )
        ) {

            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {

                Icon(
                    imageVector = Icons.Default.Devices,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = equipo.nombre,
                    style = MaterialTheme.typography.headlineMedium
                )

                Text(
                    text = "Información del equipo",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = "Tipo: ${equipo.tipo}"
                )

                Surface(
                    shape = RoundedCornerShape(50.dp),
                    color = if (equipo.disponible) {
                        MaterialTheme.colorScheme.secondaryContainer
                    } else {
                        MaterialTheme.colorScheme.errorContainer
                    }
                ) {

                    androidx.compose.foundation.layout.Row(
                        modifier = Modifier.padding(
                            horizontal = 12.dp,
                            vertical = 8.dp
                        )
                    ) {

                        Icon(
                            imageVector = if (equipo.disponible) {
                                Icons.Default.CheckCircle
                            } else {
                                Icons.Default.Block
                            },
                            contentDescription = null
                        )

                        Text(
                            text = if (equipo.disponible) {
                                "Disponible"
                            } else {
                                "No disponible"
                            },
                            modifier = Modifier.padding(start = 6.dp)
                        )
                    }
                }

                Button(
                    onClick = onSolicitarClick,
                    enabled = equipo.disponible,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text(
                        if (equipo.disponible) {
                            "Solicitar préstamo"
                        } else {
                            "Equipo no disponible"
                        }
                    )
                }
            }
        }
    }
}