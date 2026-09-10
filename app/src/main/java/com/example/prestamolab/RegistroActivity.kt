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
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegistroActivity : AppCompatActivity() {

    private lateinit var etDocumento: EditText
    private lateinit var etNombre: EditText
    private lateinit var etCorreo: EditText
    private lateinit var etPrograma: EditText
    private lateinit var etContrasena: EditText
    private lateinit var btnRegistrar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        etDocumento = findViewById(R.id.etDocumento)
        etNombre = findViewById(R.id.etNombre)
        etCorreo = findViewById(R.id.etCorreo)
        etPrograma = findViewById(R.id.etPrograma)
        etContrasena = findViewById(R.id.etContrasena)
        btnRegistrar = findViewById(R.id.btnRegistrar)

        btnRegistrar.setOnClickListener {
            registrarUsuario()
        }
    }

    private fun registrarUsuario() {
        val documentoTxt = etDocumento.text.toString().trim()
        val nombreTxt = etNombre.text.toString().trim()
        val correoTxt = etCorreo.text.toString().trim()
        val programaTxt = etPrograma.text.toString().trim()
        val contrasenaTxt = etContrasena.text.toString().trim()

        if (documentoTxt.isEmpty() || nombreTxt.isEmpty() || correoTxt.isEmpty() || programaTxt.isEmpty() || contrasenaTxt.isEmpty()) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val estudiante = Estudiante(
            documento = documentoTxt,
            nombre = nombreTxt,
            correo = correoTxt,
            programa = programaTxt,
            contrasena = contrasenaTxt
        )

        btnRegistrar.isEnabled = false

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.prestamolab.example.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        apiService.registrarEstudiante(estudiante).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                btnRegistrar.isEnabled = true
                if (response.isSuccessful) {
                    Toast.makeText(this@RegistroActivity, "Registro completado con éxito", Toast.LENGTH_SHORT).show()
                    finish()
                } else if (response.code() == 409) {
                    Toast.makeText(this@RegistroActivity, "Error: El documento o correo ya existen", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@RegistroActivity, "Error en el servidor: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                btnRegistrar.isEnabled = true
                Toast.makeText(this@RegistroActivity, "Error de red: Verifique su conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }
}