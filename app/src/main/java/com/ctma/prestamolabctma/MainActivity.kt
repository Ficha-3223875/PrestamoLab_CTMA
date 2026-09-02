package com.ctma.prestamolabctma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ctma.prestamolabctma.data.RetrofitClient
import com.ctma.prestamolabctma.data.repository.EstudianteRepository
import com.ctma.prestamolabctma.model.Estudiante
import com.ctma.prestamolabctma.ui.theme.PrestamoLabCTMATheme
import com.ctma.prestamolabctma.viewmodel.EstudianteViewModel
import com.ctma.prestamolabctma.viewmodel.EstudianteViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val repository = EstudianteRepository(RetrofitClient.apiService)
        val factory = EstudianteViewModelFactory(repository)

        setContent {
            PrestamoLabCTMATheme {
                val estudianteViewModel: EstudianteViewModel = viewModel(
                    factory = factory
                )

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    RegistroEstudiante(
                        viewModel = estudianteViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun RegistroEstudiante(
    viewModel: EstudianteViewModel,
    modifier: Modifier = Modifier
) {

    var documento by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var programa by remember { mutableStateOf("") }
    var ficha by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Registrar Estudiante"
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = documento,
            onValueChange = { documento = it },
            label = { Text("Documento") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo institucional") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = programa,
            onValueChange = { programa = it },
            label = { Text("Programa") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = ficha,
            onValueChange = { ficha = it },
            label = { Text("Ficha") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {

                val estudiante = Estudiante(
                    documento = documento,
                    nombre = nombre,
                    correoInstitucional = correo,
                    programa = programa,
                    ficha = ficha
                )

                viewModel.registrarEstudiante(estudiante)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar estudiante")
        }
    }
}