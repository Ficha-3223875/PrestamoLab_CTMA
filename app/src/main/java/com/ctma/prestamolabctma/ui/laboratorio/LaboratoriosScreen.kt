package com.ctma.prestamolabctma.ui.laboratorio

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Block
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ctma.prestamolabctma.model.Laboratorio

@Composable
fun LaboratoriosScreen(
    laboratorios: List<Laboratorio>,
    onAgregar: (String, String) -> Boolean,
    onEditar: (Int, String, String, String) -> Boolean,
    onEliminar: (Int) -> Unit,
    onCambiarEstado: (Int, String) -> Unit,
    onVolver: () -> Unit
) {

    var mostrarAgregar by remember {
        mutableStateOf(false)
    }

    var laboratorioEditar by remember {
        mutableStateOf<Laboratorio?>(null)
    }

    var laboratorioEliminar by remember {
        mutableStateOf<Laboratorio?>(null)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp)
    ) {

        Text(
            text = "Laboratorios",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Administra los espacios de formación",
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
                    "Agregar",
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

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 20.dp)
        ) {

            items(laboratorios) { laboratorio ->

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
                                imageVector = Icons.Default.Business,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )

                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 12.dp)
                            ) {

                                Text(
                                    text = laboratorio.nombre,
                                    style = MaterialTheme.typography.titleMedium
                                )

                                Text(
                                    text = "Código: ${laboratorio.codigo}",
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        val disponible =
                            laboratorio.estado.equals(
                                "Disponible",
                                ignoreCase = true
                            )

                        Surface(
                            shape = RoundedCornerShape(50.dp),
                            color = if (disponible) {
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
                                    imageVector = if (disponible) {
                                        Icons.Default.CheckCircle
                                    } else {
                                        Icons.Default.Block
                                    },
                                    contentDescription = null
                                )

                                Text(
                                    text = laboratorio.estado,
                                    modifier = Modifier.padding(
                                        start = 6.dp
                                    )
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            IconButton(
                                onClick = {
                                    laboratorioEditar = laboratorio
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Editar"
                                )
                            }

                            IconButton(
                                onClick = {
                                    laboratorioEliminar = laboratorio
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Eliminar",
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }

                        FilledTonalButton(
                            onClick = {

                                val nuevoEstado =
                                    if (disponible) {
                                        "No disponible"
                                    } else {
                                        "Disponible"
                                    }

                                onCambiarEstado(
                                    laboratorio.id,
                                    nuevoEstado
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Text(
                                if (disponible) {
                                    "Marcar no disponible"
                                } else {
                                    "Marcar disponible"
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    if (mostrarAgregar) {

        FormularioLaboratorioDialog(
            titulo = "Agregar laboratorio",
            laboratorio = null,
            onConfirmar = { nombre, codigo, _ ->

                val resultado = onAgregar(
                    nombre,
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

    laboratorioEditar?.let { laboratorio ->

        FormularioLaboratorioDialog(
            titulo = "Editar laboratorio",
            laboratorio = laboratorio,
            onConfirmar = { nombre, codigo, estado ->

                val resultado = onEditar(
                    laboratorio.id,
                    nombre,
                    codigo,
                    estado
                )

                if (resultado) {
                    laboratorioEditar = null
                }

                resultado
            },
            onCancelar = {
                laboratorioEditar = null
            }
        )
    }

    laboratorioEliminar?.let { laboratorio ->

        AlertDialog(
            onDismissRequest = {
                laboratorioEliminar = null
            },
            title = {
                Text("Eliminar laboratorio")
            },
            text = {
                Text(
                    "¿Deseas eliminar el laboratorio \"${laboratorio.nombre}\"?"
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onEliminar(laboratorio.id)
                        laboratorioEliminar = null
                    }
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        laboratorioEliminar = null
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
private fun FormularioLaboratorioDialog(
    titulo: String,
    laboratorio: Laboratorio?,
    onConfirmar: (
        String,
        String,
        String
    ) -> Boolean,
    onCancelar: () -> Unit
) {

    var nombre by remember {
        mutableStateOf(laboratorio?.nombre ?: "")
    }

    var codigo by remember {
        mutableStateOf(laboratorio?.codigo ?: "")
    }

    var estado by remember {
        mutableStateOf(laboratorio?.estado ?: "Disponible")
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

                if (laboratorio != null) {

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
                        codigo.isBlank()
                    ) {

                        error =
                            "Nombre y código son obligatorios"

                        return@TextButton
                    }

                    val resultado = onConfirmar(
                        nombre.trim(),
                        codigo.trim(),
                        estado.trim()
                    )

                    if (!resultado) {
                        error =
                            "El código ya existe o los datos no son válidos"
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