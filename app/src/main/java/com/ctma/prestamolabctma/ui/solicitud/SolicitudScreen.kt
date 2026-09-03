package com.ctma.prestamolabctma.ui.solicitud

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Solicitar préstamo"
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        OutlinedTextField(
            value = fechaPrestamo,
            onValueChange = {
                fechaPrestamo = it
            },
            label = {
                Text("Fecha de préstamo")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        OutlinedTextField(
            value = fechaDevolucion,
            onValueChange = {
                fechaDevolucion = it
            },
            label = {
                Text("Fecha de devolución")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        OutlinedTextField(
            value = motivo,
            onValueChange = {
                motivo = it
            },
            label = {
                Text("Motivo del préstamo")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Button(
            onClick = {

                val solicitud = Solicitud(
                    id = 0,
                    equipo = equipo,
                    fechaPrestamo = fechaPrestamo,
                    fechaDevolucion = fechaDevolucion,
                    motivo = motivo,
                    estado = "Pendiente"
                )

                onSolicitudEnviada(solicitud)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar solicitud")
        }
    }
}