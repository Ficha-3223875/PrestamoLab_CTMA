package com.ctma.prestamolabctma.ui.catalogo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ctma.prestamolabctma.model.Equipo

@Composable
fun CatalogoScreen(
    onEquipoClick: (Equipo) -> Unit
) {

    val equipos = listOf(
        Equipo(1, "Portátil Lenovo", "Computador", true),
        Equipo(2, "Proyector Epson", "Proyección", true),
        Equipo(3, "Cámara Sony", "Cámara", false),
        Equipo(4, "Tablet Samsung", "Tablet", true)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = "Catálogo"
        )

        Text(
            text = "Equipos disponibles para préstamo"
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(equipos) { equipo ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onEquipoClick(equipo)
                        }
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = equipo.nombre
                        )

                        Text(
                            text = "Tipo: ${equipo.tipo}"
                        )

                        Text(
                            text = if (equipo.disponible) {
                                "Disponible"
                            } else {
                                "No disponible"
                            }
                        )
                    }
                }
            }
        }
    }
}