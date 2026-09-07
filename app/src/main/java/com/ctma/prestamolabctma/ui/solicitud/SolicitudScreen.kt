package com.ctma.prestamolabctma.ui.solicitud

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ctma.prestamolabctma.model.Equipo
import com.ctma.prestamolabctma.model.Solicitud
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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

    var mostrarCalendarioPrestamo by remember {
        mutableStateOf(false)
    }

    var mostrarCalendarioDevolucion by remember {
        mutableStateOf(false)
    }

    var fechaPrestamoMillis by remember {
        mutableStateOf<Long?>(null)
    }

    var fechaDevolucionMillis by remember {
        mutableStateOf<Long?>(null)
    }

    val formatoFecha = remember {
        SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.getDefault()
        )
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
            onValueChange = {},
            readOnly = true,
            label = {
                Text("Fecha de préstamo")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                mostrarCalendarioPrestamo = true
                error = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Seleccionar fecha de préstamo")
        }

        OutlinedTextField(
            value = fechaDevolucion,
            onValueChange = {},
            readOnly = true,
            label = {
                Text("Fecha de devolución")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                mostrarCalendarioDevolucion = true
                error = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Seleccionar fecha de devolución")
        }

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
                        error = "Selecciona la fecha de préstamo."
                    }

                    fechaDevolucion.isBlank() -> {
                        error = "Selecciona la fecha de devolución."
                    }

                    fechaPrestamoMillis == null -> {
                        error = "Selecciona la fecha de préstamo."
                    }

                    fechaDevolucionMillis == null -> {
                        error = "Selecciona la fecha de devolución."
                    }

                    fechaDevolucionMillis!! <= fechaPrestamoMillis!! -> {
                        error = "La fecha de devolución debe ser posterior a la fecha de préstamo."
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

    if (mostrarCalendarioPrestamo) {

        val estadoFecha = rememberDatePickerState()

        DatePickerDialog(
            onDismissRequest = {
                mostrarCalendarioPrestamo = false
            },
            confirmButton = {

                TextButton(
                    onClick = {

                        estadoFecha.selectedDateMillis?.let { millis ->

                            fechaPrestamoMillis = millis

                            fechaPrestamo =
                                formatoFecha.format(
                                    Date(millis)
                                )

                            error = ""
                        }

                        mostrarCalendarioPrestamo = false
                    }
                ) {
                    Text("Aceptar")
                }
            },
            dismissButton = {

                TextButton(
                    onClick = {
                        mostrarCalendarioPrestamo = false
                    }
                ) {
                    Text("Cancelar")
                }
            }
        ) {

            DatePicker(
                state = estadoFecha
            )
        }
    }

    if (mostrarCalendarioDevolucion) {

        val estadoFecha = rememberDatePickerState()

        DatePickerDialog(
            onDismissRequest = {
                mostrarCalendarioDevolucion = false
            },
            confirmButton = {

                TextButton(
                    onClick = {

                        estadoFecha.selectedDateMillis?.let { millis ->

                            fechaDevolucionMillis = millis

                            fechaDevolucion =
                                formatoFecha.format(
                                    Date(millis)
                                )

                            error = ""
                        }

                        mostrarCalendarioDevolucion = false
                    }
                ) {
                    Text("Aceptar")
                }
            },
            dismissButton = {

                TextButton(
                    onClick = {
                        mostrarCalendarioDevolucion = false
                    }
                ) {
                    Text("Cancelar")
                }
            }
        ) {

            DatePicker(
                state = estadoFecha
            )
        }
    }
}