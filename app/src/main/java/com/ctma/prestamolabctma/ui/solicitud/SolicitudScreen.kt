package com.ctma.prestamolabctma.ui.solicitud

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ctma.prestamolabctma.model.Equipo
import com.ctma.prestamolabctma.model.Solicitud

@Composable
fun SolicitudScreen(
    equipo: Equipo,
    onSolicitudEnviada: (Solicitud) -> Unit
) {

    var fechaPrestamo by remember {
        mutableStateOf("")
    }

    var fechaDevolucion by remember {
        mutableStateOf("")
    }

    var motivo by remember {
        mutableStateOf("")
    }

    var error by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = "Nueva solicitud",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Equipo: ${equipo.nombre}",
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

        OutlinedTextField(
            value = fechaPrestamo,
            onValueChange = {
                fechaPrestamo = it
                error = ""
            },
            label = {
                Text("Fecha de préstamo")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = fechaDevolucion,
            onValueChange = {
                fechaDevolucion = it
                error = ""
            },
            label = {
                Text("Fecha de devolución")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = motivo,
            onValueChange = {
                motivo = it
                error = ""
            },
            label = {
                Text("Motivo de la solicitud")
            },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )

        if (error.isNotEmpty()) {

            Text(
                text = error,
                color = MaterialTheme.colorScheme.error
            )
        }

        Button(
            onClick = {

                when {

                    !equipo.disponible -> {

                        error = "Este equipo no está disponible."
                    }

                    fechaPrestamo.isBlank() -> {

                        error = "Ingresa la fecha de préstamo."
                    }

                    fechaDevolucion.isBlank() -> {

                        error = "Ingresa la fecha de devolución."
                    }

                    motivo.isBlank() -> {

                        error = "Ingresa el motivo de la solicitud."
                    }

                    else -> {

                        val solicitud = Solicitud(
                            id = System.currentTimeMillis().toInt(),
                            equipo = equipo,
                            fechaPrestamo = fechaPrestamo,
                            fechaDevolucion = fechaDevolucion,
                            motivo = motivo,
                            estado = "Pendiente"
                        )

                        onSolicitudEnviada(solicitud)
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = "Enviar solicitud"
            )
        }
    }
}