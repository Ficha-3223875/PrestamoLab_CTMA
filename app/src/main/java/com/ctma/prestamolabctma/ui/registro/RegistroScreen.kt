package com.ctma.prestamolabctma.ui.registro

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = "Crear cuenta",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Registra tus datos para solicitar préstamos."
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
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
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
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
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
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
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
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
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
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
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
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
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

                    documento.isBlank() -> {
                        error = "Ingresa el documento."
                    }

                    nombre.isBlank() -> {
                        error = "Ingresa el nombre completo."
                    }

                    correo.isBlank() -> {
                        error = "Ingresa el correo institucional."
                    }

                    !correo.endsWith("@sena.edu.co", ignoreCase = true) -> {
                        error = "Debes utilizar un correo institucional del SENA."
                    }

                    password.isBlank() -> {
                        error = "Ingresa una contraseña."
                    }

                    programa.isBlank() -> {
                        error = "Ingresa el programa de formación."
                    }

                    ficha.isBlank() -> {
                        error = "Ingresa la ficha."
                    }

                    else -> {

                        val usuario = Usuario(
                            documento = documento,
                            nombre = nombre,
                            correo = correo,
                            password = password,
                            programa = programa,
                            ficha = ficha
                        )

                        onRegistroExitoso(usuario)
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = "Registrarme"
            )
        }

        Button(
            onClick = onVolverLogin,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = "Volver al inicio de sesión"
            )
        }
    }
}