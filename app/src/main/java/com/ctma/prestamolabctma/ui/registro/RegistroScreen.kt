package com.ctma.prestamolabctma.ui.registro

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.ctma.prestamolabctma.model.Usuario

@Composable
fun RegistroScreen(
    onRegistroExitoso: (Usuario) -> Unit,
    onVolverLogin: () -> Unit
) {
    var documento by remember {
        mutableStateOf("")
    }

    var nombre by remember {
        mutableStateOf("")
    }

    var correo by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var programa by remember {
        mutableStateOf("")
    }

    var ficha by remember {
        mutableStateOf("")
    }

    var error by remember {
        mutableStateOf("")
    }

    var mostrarPassword by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.background
            )
            .verticalScroll(
                rememberScrollState()
            )
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp),
            tonalElevation = 6.dp,
            shadowElevation = 8.dp
        ) {

            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Text(
                    text = "Crear cuenta",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = "Registra tus datos institucionales para solicitar equipos.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                OutlinedTextField(
                    value = documento,
                    onValueChange = {
                        documento = it
                        error = ""
                    },
                    label = {
                        Text("Documento")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Badge,
                            contentDescription = "Documento"
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp)
                )

                OutlinedTextField(
                    value = nombre,
                    onValueChange = {
                        nombre = it
                        error = ""
                    },
                    label = {
                        Text("Nombre completo")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Nombre"
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp)
                )

                OutlinedTextField(
                    value = correo,
                    onValueChange = {
                        correo = it
                        error = ""
                    },
                    label = {
                        Text("Correo institucional")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Correo"
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp)
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        error = ""
                    },
                    label = {
                        Text("Contraseña")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Contraseña"
                        )
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                mostrarPassword = !mostrarPassword
                            }
                        ) {
                            Icon(
                                imageVector =
                                    if (mostrarPassword) {
                                        Icons.Default.VisibilityOff
                                    } else {
                                        Icons.Default.Visibility
                                    },
                                contentDescription =
                                    if (mostrarPassword) {
                                        "Ocultar contraseña"
                                    } else {
                                        "Mostrar contraseña"
                                    }
                            )
                        }
                    },
                    visualTransformation =
                        if (mostrarPassword) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp)
                )

                OutlinedTextField(
                    value = programa,
                    onValueChange = {
                        programa = it
                        error = ""
                    },
                    label = {
                        Text("Programa de formación")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.School,
                            contentDescription = "Programa"
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp)
                )

                OutlinedTextField(
                    value = ficha,
                    onValueChange = {
                        ficha = it
                        error = ""
                    },
                    label = {
                        Text("Ficha")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Badge,
                            contentDescription = "Ficha"
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp)
                )

                if (error.isNotEmpty()) {

                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Button(
                    onClick = {

                        when {

                            documento.isBlank() -> {
                                error = "Ingresa el documento."
                            }

                            nombre.isBlank() -> {
                                error = "Ingresa el nombre completo."
                            }

                            correo.isBlank() -> {
                                error =
                                    "Ingresa el correo institucional."
                            }

                            !correo.endsWith(
                                "@sena.edu.co",
                                ignoreCase = true
                            ) -> {
                                error =
                                    "Debes utilizar un correo institucional del SENA."
                            }

                            password.isBlank() -> {
                                error =
                                    "Ingresa una contraseña."
                            }

                            programa.isBlank() -> {
                                error =
                                    "Ingresa el programa de formación."
                            }

                            ficha.isBlank() -> {
                                error = "Ingresa la ficha."
                            }

                            else -> {

                                val usuario = Usuario(
                                    documento = documento.trim(),
                                    nombre = nombre.trim(),
                                    correo = correo.trim(),
                                    password = password,
                                    programa = programa.trim(),
                                    ficha = ficha.trim(),
                                    rol = "Aprendiz"
                                )

                                onRegistroExitoso(usuario)
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor =
                            MaterialTheme.colorScheme.primary
                    )
                ) {

                    Text(
                        text = "Crear cuenta",
                        fontWeight = FontWeight.Bold
                    )
                }

                TextButton(
                    onClick = onVolverLogin,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text(
                        text = "Volver al inicio de sesión",
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}