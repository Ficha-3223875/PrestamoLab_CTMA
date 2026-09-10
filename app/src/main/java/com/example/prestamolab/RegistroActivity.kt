package com.example.prestamolab

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.prestamolab.api.ApiService
import com.example.prestamolab.model.Estudiante
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val etDocumento = findViewById<EditText>(R.id.etDocumento)
        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etCorreo = findViewById<EditText>(R.id.etCorreo)
        val etFicha = findViewById<EditText>(R.id.etFicha)
        val etContrasena = findViewById<EditText>(R.id.etContrasena)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)

        btnRegistrar.setOnClickListener {
            val documento = etDocumento.text.toString().trim()
            val nombre = etNombre.text.toString().trim().replace(Regex("[<>&\"']"), "")
            val correo = etCorreo.text.toString().trim().lowercase()
            val ficha = etFicha.text.toString().trim()
            val contrasena = etContrasena.text.toString().trim()

            if (documento.isEmpty() || nombre.isEmpty() || correo.isEmpty() || ficha.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this@RegistroActivity, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val dominiosValidos = listOf("@sena.edu.co", "@soy.sena.edu.co", "@misena.edu.co")
            val esCorreoValido = dominiosValidos.any { correo.endsWith(it) }

            if (!esCorreoValido) {
                etCorreo.error = "Debe usar un correo institucional válido (@sena.edu.co / @soy.sena.edu.co)"
                Toast.makeText(this@RegistroActivity, "El correo no pertenece a un dominio institucional habilitado", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val estudiante = Estudiante(
                id = documento,
                nombre = nombre,
                email = correo,
                ficha = ficha,
                pass = contrasena
            )

            btnRegistrar.isEnabled = false

            ApiService.create().registrarEstudiante(estudiante).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    btnRegistrar.isEnabled = true
                    if (response.isSuccessful) {
                        Toast.makeText(this@RegistroActivity, "Registro completado con éxito", Toast.LENGTH_SHORT).show()
                        finish()
                    } else if (response.code() == 409) {
                        Toast.makeText(this@RegistroActivity, "Error: El documento o correo ya se encuentra registrado", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this@RegistroActivity, "Error en el servidor: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    btnRegistrar.isEnabled = true
                    Toast.makeText(this@RegistroActivity, "Error de red: Verifique su conexión a internet", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}