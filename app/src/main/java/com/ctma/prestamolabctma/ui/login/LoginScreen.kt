package com.ctma.prestamolabctma.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.ctma.prestamolabctma.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    onLoginSuccess: () -> Unit,
    onRegistroClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    var correo by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var mostrarPassword by remember {
        mutableStateOf(false)
    }

    val mensaje by loginViewModel.mensaje.collectAsState()
    val loginExitoso by loginViewModel.loginExitoso.collectAsState()
    val cargando by loginViewModel.cargando.collectAsState()

    /*
     * Cuando el login sea exitoso,
     * navegamos hacia Home.
     */
    LaunchedEffect(loginExitoso) {

        if (loginExitoso) {

            loginViewModel.limpiarLoginExitoso()

            onLoginSuccess()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.background
            ),
        contentAlignment = Alignment.Center
    ) {

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            shape = RoundedCornerShape(28.dp),
            tonalElevation = 6.dp,
            shadowElevation = 8.dp
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                // =================================================
                // ENCABEZADO
                // =================================================

                Text(
                    text = "PréstamoLab",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = "CTMA",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "Gestión de préstamos de equipos",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(
                    modifier = Modifier.height(28.dp)
                )

                // =================================================
                // CORREO
                // =================================================

                OutlinedTextField(
                    value = correo,
                    onValueChange = {
                        correo = it
                    },
                    label = {
                        Text("Correo institucional")
                    },
                    placeholder = {
                        Text("correo@sena.edu.co")
                    },
                    leadingIcon = {

                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Correo institucional"
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp)
                )

                Spacer(
                    modifier = Modifier.height(14.dp)
                )

                // =================================================
                // CONTRASEÑA
                // =================================================

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
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

                Spacer(
                    modifier = Modifier.height(22.dp)
                )

                // =================================================
                // BOTÓN INICIAR SESIÓN
                // =================================================

                Button(
                    onClick = {

                        loginViewModel.iniciarSesion(
                            correo = correo.trim(),
                            password = password
                        )
                    },
                    enabled = !cargando,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor =
                            MaterialTheme.colorScheme.primary
                    )
                ) {

                    if (cargando) {

                        CircularProgressIndicator(
                            modifier = Modifier.height(22.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                    } else {

                        Text(
                            text = "Iniciar sesión",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // =================================================
                // MENSAJE
                // =================================================

                if (mensaje.isNotEmpty()) {

                    Spacer(
                        modifier = Modifier.height(14.dp)
                    )

                    Text(
                        text = mensaje,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                // =================================================
                // REGISTRO
                // =================================================

                Text(
                    text = "¿Aún no tienes una cuenta?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                TextButton(
                    onClick = onRegistroClick
                ) {

                    Text(
                        text = "Crear cuenta",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}