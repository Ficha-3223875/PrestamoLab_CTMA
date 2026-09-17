package com.ctma.prestamolabctma.ui.equipo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Block
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Surface
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
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp)
    ) {

        Text(
            text = "Equipos",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Administra el inventario de equipos",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 4.dp, bottom = 18.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Button(
                onClick = {
                    mostrarAgregar = true
                },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(14.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )

                Text(
                    text = "Agregar",
                    modifier = Modifier.padding(start = 6.dp)
                )
            }

            FilledTonalButton(
                onClick = onVolver,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("Volver")
            }
        }

        if (equipos.isEmpty()) {

            Text(
                text = "No hay equipos registrados.",
                modifier = Modifier.padding(top = 20.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

        } else {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 20.dp)
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
                        onReportarIncidente = onReportarIncidente,
                        onFinalizarMantenimiento = onFinalizarMantenimiento
                    )
                }
            }
        }
    }

    if (mostrarAgregar) {

        FormularioEquipoDialog(
            titulo = "Agregar equipo",
            equipo = null,
            onConfirmar = { nombre, tipo, codigo, _, _ ->

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

    equipoEditar?.let { equipo ->

        FormularioEquipoDialog(
            titulo = "Editar equipo",
            equipo = equipo,
            onConfirmar = { nombre, tipo, codigo, estado, disponible ->

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
                    "¿Deseas eliminar el equipo \"${equipo.nombre}\"?"
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onEliminar(equipo.id)
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
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                Icon(
                    imageVector = Icons.Default.Build,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 12.dp)
                ) {

                    Text(
                        text = equipo.nombre,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = equipo.tipo,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Text(
                text = "Código: ${equipo.codigo}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Estado: ${equipo.estado}",
                style = MaterialTheme.typography.bodyMedium
            )

            Surface(
                shape = RoundedCornerShape(50.dp),
                color = if (equipo.disponible) {
                    MaterialTheme.colorScheme.secondaryContainer
                } else {
                    MaterialTheme.colorScheme.errorContainer
                }
            ) {
                Row(
                    modifier = Modifier.padding(
                        horizontal = 12.dp,
                        vertical = 7.dp
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                IconButton(
                    onClick = onEditar
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar"
                    )
                }

                IconButton(
                    onClick = onEliminar
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }

            if (
                equipo.estado.equals(
                    "En Mantenimiento",
                    ignoreCase = true
                )
            ) {

                Text(
                    text = "Este equipo está en mantenimiento.",
                    color = MaterialTheme.colorScheme.error
                )

                Button(
                    onClick = {
                        onFinalizarMantenimiento(equipo)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text("Finalizar mantenimiento")
                }

            } else {

                FilledTonalButton(
                    onClick = {
                        observacion = ""
                        mostrarDialogo = true
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                ) {

                    Icon(
                        imageVector = Icons.Default.ReportProblem,
                        contentDescription = null
                    )

                    Text(
                        text = "Reportar incidente",
                        modifier = Modifier.padding(start = 6.dp)
                    )
                }
            }
        }
    }

    if (mostrarDialogo) {

        AlertDialog(
            onDismissRequest = {
                mostrarDialogo = false
            },
            title = {
                Text("Reportar incidente")
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    Text("Equipo: ${equipo.nombre}")
                    Text("Código: ${equipo.codigo}")

                    OutlinedTextField(
                        value = observacion,
                        onValueChange = {
                            observacion = it
                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text("Descripción del incidente")
                        },
                        minLines = 3
                    )
                }
            },
            confirmButton = {

                TextButton(
                    onClick = {

                        if (observacion.isNotBlank()) {

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
        mutableStateOf(equipo?.nombre ?: "")
    }

    var tipo by remember {
        mutableStateOf(equipo?.tipo ?: "")
    }

    var codigo by remember {
        mutableStateOf(equipo?.codigo ?: "")
    }

    var estado by remember {
        mutableStateOf(equipo?.estado ?: "Disponible")
    }

    var disponible by remember {
        mutableStateOf(equipo?.disponible ?: true)
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
                verticalArrangement = Arrangement.spacedBy(12.dp)
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
                        color = MaterialTheme.colorScheme.error
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