package com.ctma.prestamolabctma.ui.laboratorio

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
            .padding(24.dp)
    ) {

        Text(
            text = "Laboratorios",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Gestión de laboratorios",
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

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(laboratorios) { laboratorio ->

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement =
                            Arrangement.spacedBy(8.dp)
                    ) {

                        Text(
                            text = laboratorio.nombre,
                            style = MaterialTheme
                                .typography
                                .titleMedium
                        )

                        Text(
                            text = "Código: ${laboratorio.codigo}"
                        )

                        Text(
                            text = "Estado: ${laboratorio.estado}"
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement =
                                Arrangement.spacedBy(8.dp)
                        ) {

                            Button(
                                onClick = {
                                    laboratorioEditar =
                                        laboratorio
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Editar")
                            }

                            Button(
                                onClick = {
                                    laboratorioEliminar =
                                        laboratorio
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Eliminar")
                            }
                        }

                        Button(
                            onClick = {

                                val nuevoEstado =
                                    if (
                                        laboratorio.estado
                                            .equals(
                                                "Disponible",
                                                ignoreCase = true
                                            )
                                    ) {
                                        "No disponible"
                                    } else {
                                        "Disponible"
                                    }

                                onCambiarEstado(
                                    laboratorio.id,
                                    nuevoEstado
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            Text(
                                text =
                                    if (
                                        laboratorio.estado
                                            .equals(
                                                "Disponible",
                                                ignoreCase = true
                                            )
                                    ) {
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

    // =========================================================
    // AGREGAR LABORATORIO
    // =========================================================

    if (mostrarAgregar) {

        FormularioLaboratorioDialog(
            titulo = "Agregar laboratorio",
            laboratorio = null,

            onConfirmar = { nombre, codigo, estado ->

                onAgregar(
                    nombre,
                    codigo
                )
            },

            onCancelar = {
                mostrarAgregar = false
            }
        )
    }

// =========================================================
// EDITAR LABORATORIO
// =========================================================

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

    // =========================================================
    // ELIMINAR LABORATORIO
    // =========================================================

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
                    "¿Deseas eliminar el laboratorio " +
                            "\"${laboratorio.nombre}\"?"
                )
            },

            confirmButton = {

                TextButton(
                    onClick = {

                        onEliminar(
                            laboratorio.id
                        )

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
        mutableStateOf(
            laboratorio?.nombre ?: ""
        )
    }

    var codigo by remember {
        mutableStateOf(
            laboratorio?.codigo ?: ""
        )
    }

    var estado by remember {
        mutableStateOf(
            laboratorio?.estado ?: "Disponible"
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

                    if (laboratorio == null) {

                        if (resultado) {
                            onCancelar()
                        } else {
                            error =
                                "El código ya existe"
                        }
                    } else {

                        if (!resultado) {
                            error =
                                "El código ya existe o los datos no son válidos"
                        }
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