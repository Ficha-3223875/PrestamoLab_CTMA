package com.ctma.prestamolabctma.ui.metricas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ctma.prestamolabctma.viewmodel.Metricas

@Composable
fun MetricasScreen(
    metricas: Metricas,
    onVolver: () -> Unit
) {

    val datos = listOf(
        Triple(
            "Total de solicitudes",
            metricas.totalSolicitudes,
            Icons.Default.Assignment
        ),
        Triple(
            "Pendientes",
            metricas.pendientes,
            Icons.Default.Schedule
        ),
        Triple(
            "Aprobadas",
            metricas.aprobadas,
            Icons.Default.CheckCircle
        ),
        Triple(
            "Rechazadas",
            metricas.rechazadas,
            Icons.Default.Close
        ),
        Triple(
            "Canceladas",
            metricas.canceladas,
            Icons.Default.Close
        ),
        Triple(
            "Préstamos activos",
            metricas.prestamosActivos,
            Icons.Default.TrendingUp
        ),
        Triple(
            "Devueltos",
            metricas.devueltos,
            Icons.Default.CheckCircle
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp)
    ) {

        Text(
            text = "Métricas",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Resumen de préstamos y solicitudes",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(
                top = 4.dp,
                bottom = 18.dp
            )
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(datos) { dato ->

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 3.dp
                    )
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Icon(
                            imageVector = dato.third,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )

                        Text(
                            text = dato.first,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 10.dp)
                        )

                        Text(
                            text = dato.second.toString(),
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(top = 6.dp)
                        )
                    }
                }
            }
        }

        Button(
            onClick = onVolver,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Volver")
        }
    }
}