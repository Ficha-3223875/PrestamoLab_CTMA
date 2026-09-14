package com.ctma.prestamolabctma.ui.equipo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ctma.prestamolabctma.model.Equipo

@Composable
fun EquiposScreen(
    equipos: List<Equipo>,
    onAgregar: (String, String, String) -> Boolean,
    onEditar: (
        Int,
        String,
        String,
        String,
        String,
        Boolean
    ) -> Boolean,
    onEliminar: (Int) -> Unit,
    onReportarIncidente: (Equipo, String) -> Unit,
    onFinalizarMantenimiento: (Equipo) -> Unit,
    onVolver: () -> Unit
) {

    var mostrarAgregar by remember {
        mutableStateOf(false)
    }

    var equipoEditar by remember {
        mutableStateOf<Equipo?>(null)
    }

    var equipoEliminar by remember {
        mutableStateOf<Equipo?>(null)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = "Equipos",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Gestión de equipos",
            modifier = Modifier.padding(
                top = 8.dp,
                bottom = 16.dp
            )
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Button(
                onClick = {
                    mostrarAgregar = true
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Agregar")
            }

            Button(
                onClick = onVolver,
                modifier = Modifier.weight(1f)
            ) {
                Text("Volver")
            }
        }

        if (equipos.isEmpty()) {

            Text(
                text = "No hay equipos registrados.",
                modifier = Modifier.padding(
                    top = 16.dp
                )
            )

        } else {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp),
                verticalArrangement =
                    Arrangement.spacedBy(12.dp),
                contentPadding =
                    PaddingValues(bottom = 16.dp)
            ) {

                items(equipos) { equipo ->

                    EquipoCard(
                        equipo = equipo,

                        onEditar = {
                            equipoEditar = equipo
                        },

                        onEliminar = {
                            equipoEliminar = equipo
                        },

                        onReportarIncidente =
                            onReportarIncidente,

                        onFinalizarMantenimiento =
                            onFinalizarMantenimiento
                    )
                }
            }
        }
    }

    // =====================================================
    // AGREGAR EQUIPO
    // =====================================================

    if (mostrarAgregar) {

        FormularioEquipoDialog(
            titulo = "Agregar equipo",
            equipo = null,

            onConfirmar = {
                    nombre,
                    tipo,
                    codigo,
                    estado,
                    disponible ->

                val resultado = onAgregar(
                    nombre,
                    tipo,
                    codigo
                )

                if (resultado) {
                    mostrarAgregar = false
                }

                resultado
            },

            onCancelar = {
                mostrarAgregar = false
            }
        )
    }

    // =====================================================
    // EDITAR EQUIPO
    // =====================================================

    equipoEditar?.let { equipo ->

        FormularioEquipoDialog(
            titulo = "Editar equipo",
            equipo = equipo,

            onConfirmar = {
                    nombre,
                    tipo,
                    codigo,
                    estado,
                    disponible ->

                val resultado = onEditar(
                    equipo.id,
                    nombre,
                    tipo,
                    codigo,
                    estado,
                    disponible
                )

                if (resultado) {
                    equipoEditar = null
                }

                resultado
            },

            onCancelar = {
                equipoEditar = null
            }
        )
    }

    // =====================================================
    // ELIMINAR EQUIPO
    // =====================================================

    equipoEliminar?.let { equipo ->

        AlertDialog(
            onDismissRequest = {
                equipoEliminar = null
            },

            title = {
                Text("Eliminar equipo")
            },

            text = {
                Text(
                    "¿Deseas eliminar el equipo " +
                            "\"${equipo.nombre}\"?"
                )
            },

            confirmButton = {

                TextButton(
                    onClick = {

                        onEliminar(
                            equipo.id
                        )

                        equipoEliminar = null
                    }
                ) {
                    Text("Eliminar")
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {
                        equipoEliminar = null
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}

// =========================================================
// TARJETA DEL EQUIPO
// =========================================================

@Composable
fun EquipoCard(
    equipo: Equipo,
    onEditar: () -> Unit,
    onEliminar: () -> Unit,
    onReportarIncidente: (Equipo, String) -> Unit,
    onFinalizarMantenimiento: (Equipo) -> Unit
) {

    var mostrarDialogo by remember {
        mutableStateOf(false)
    }

    var observacion by remember {
        mutableStateOf("")
    }

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement =
                Arrangement.spacedBy(8.dp)
        ) {

            Text(
                text = equipo.nombre,
                style = MaterialTheme
                    .typography
                    .titleMedium
            )

            Text(
                text = "Código: ${equipo.codigo}"
            )

            Text(
                text = "Tipo: ${equipo.tipo}"
            )

            Text(
                text = "Estado: ${equipo.estado}"
            )

            Text(
                text =
                    if (equipo.disponible) {
                        "Disponibilidad: Disponible"
                    } else {
                        "Disponibilidad: No disponible"
                    }
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.spacedBy(8.dp)
            ) {

                Button(
                    onClick = onEditar,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Editar")
                }

                Button(
                    onClick = onEliminar,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Eliminar")
                }
            }

            if (
                equipo.estado.equals(
                    "En Mantenimiento",
                    ignoreCase = true
                )
            ) {

                Text(
                    text =
                        "⚠️ Este equipo no está disponible para préstamos."
                )

                Button(
                    onClick = {
                        onFinalizarMantenimiento(
                            equipo
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Finalizar mantenimiento")
                }

            } else {

                Button(
                    onClick = {
                        observacion = ""
                        mostrarDialogo = true
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Reportar incidente")
                }
            }
        }
    }

    // =====================================================
    // DIÁLOGO DE INCIDENTE
    // =====================================================

    if (mostrarDialogo) {

        AlertDialog(
            onDismissRequest = {
                mostrarDialogo = false
            },

            title = {
                Text("Reportar incidente")
            },

            text = {

                Column {

                    Text(
                        text =
                            "Equipo: ${equipo.nombre}"
                    )

                    Text(
                        text =
                            "Código: ${equipo.codigo}",
                        modifier = Modifier.padding(
                            top = 4.dp
                        )
                    )

                    OutlinedTextField(
                        value = observacion,

                        onValueChange = {
                            observacion = it
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp),

                        label = {
                            Text("Observación")
                        },

                        minLines = 3
                    )
                }
            },

            confirmButton = {

                TextButton(
                    onClick = {

                        if (
                            observacion.isNotBlank()
                        ) {

                            onReportarIncidente(
                                equipo,
                                observacion.trim()
                            )

                            mostrarDialogo = false
                            observacion = ""
                        }
                    }
                ) {
                    Text("Reportar")
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {
                        mostrarDialogo = false
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}

// =========================================================
// FORMULARIO DE EQUIPO
// =========================================================

@Composable
private fun FormularioEquipoDialog(
    titulo: String,
    equipo: Equipo?,
    onConfirmar: (
        String,
        String,
        String,
        String,
        Boolean
    ) -> Boolean,
    onCancelar: () -> Unit
) {

    var nombre by remember {
        mutableStateOf(
            equipo?.nombre ?: ""
        )
    }

    var tipo by remember {
        mutableStateOf(
            equipo?.tipo ?: ""
        )
    }

    var codigo by remember {
        mutableStateOf(
            equipo?.codigo ?: ""
        )
    }

    var estado by remember {
        mutableStateOf(
            equipo?.estado ?: "Disponible"
        )
    }

    var disponible by remember {
        mutableStateOf(
            equipo?.disponible ?: true
        )
    }

    var error by remember {
        mutableStateOf("")
    }

    AlertDialog(

        onDismissRequest = onCancelar,

        title = {
            Text(titulo)
        },

        text = {

            Column(
                verticalArrangement =
                    Arrangement.spacedBy(12.dp)
            ) {

                OutlinedTextField(
                    value = nombre,

                    onValueChange = {
                        nombre = it
                        error = ""
                    },

                    modifier = Modifier.fillMaxWidth(),

                    label = {
                        Text("Nombre")
                    },

                    singleLine = true
                )

                OutlinedTextField(
                    value = tipo,

                    onValueChange = {
                        tipo = it
                        error = ""
                    },

                    modifier = Modifier.fillMaxWidth(),

                    label = {
                        Text("Tipo")
                    },

                    singleLine = true
                )

                OutlinedTextField(
                    value = codigo,

                    onValueChange = {
                        codigo = it
                        error = ""
                    },

                    modifier = Modifier.fillMaxWidth(),

                    label = {
                        Text("Código")
                    },

                    placeholder = {
                        Text("Ej. EQ-005")
                    },

                    singleLine = true
                )

                if (equipo != null) {

                    OutlinedTextField(
                        value = estado,

                        onValueChange = {
                            estado = it
                            error = ""
                        },

                        modifier = Modifier.fillMaxWidth(),

                        label = {
                            Text("Estado")
                        },

                        singleLine = true
                    )

                    Button(
                        onClick = {
                            disponible = !disponible
                        },

                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Text(
                            text =
                                if (disponible) {
                                    "Disponible"
                                } else {
                                    "No disponible"
                                }
                        )
                    }
                }

                if (error.isNotBlank()) {

                    Text(
                        text = error,

                        color = MaterialTheme
                            .colorScheme
                            .error
                    )
                }
            }
        },

        confirmButton = {

            TextButton(
                onClick = {

                    if (
                        nombre.isBlank() ||
                        tipo.isBlank() ||
                        codigo.isBlank()
                    ) {

                        error =
                            "Nombre, tipo y código son obligatorios"

                        return@TextButton
                    }

                    val resultado = onConfirmar(
                        nombre.trim(),
                        tipo.trim(),
                        codigo.trim(),
                        estado.trim(),
                        disponible
                    )

                    if (!resultado) {

                        error =
                            "No se pudieron guardar los datos. Verifica que el código no esté repetido."
                    }
                }
            ) {
                Text("Guardar")
            }
        },

        dismissButton = {

            TextButton(
                onClick = onCancelar
            ) {
                Text("Cancelar")
            }
        }
    )
}