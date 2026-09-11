package com.example.prestamolab

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.prestamolab.api.RetrofitClient
import com.example.prestamolab.model.LoginRequest
import com.example.prestamolab.model.LoginResponse
import com.example.prestamolab.util.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var etCorreo: EditText
    private lateinit var etContrasena: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvIrARegistro: TextView
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sessionManager = SessionManager(this)

        if (sessionManager.isLoggedIn()) {
            startActivity(Intent(this, SolicitudActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_login)

        etCorreo = findViewById(R.id.etCorreoLogin)
        etContrasena = findViewById(R.id.etContrasenaLogin)
        btnLogin = findViewById(R.id.btnLogin)
        tvIrARegistro = findViewById(R.id.tvIrARegistro)

        btnLogin.setOnClickListener {
            realizarLoginRemoto()
        }

        tvIrARegistro.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }
    }

    private fun realizarLoginRemoto() {
        val correo = etCorreo.text.toString().trim()
        val contrasena = etContrasena.text.toString().trim()

        if (correo.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(this, "Por favor ingresa usuario y contraseña", Toast.LENGTH_SHORT).show()
            return
        }

        btnLogin.isEnabled = false
        val request = LoginRequest(correo, contrasena)

        // Consumo remoto centralizado con RetrofitClient
        RetrofitClient.instance.iniciarSesion(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                btnLogin.isEnabled = true
                if (response.isSuccessful && response.body() != null) {
                    val body = response.body()!!
                    sessionManager.guardarSesion(body.token, correo)
                    Toast.makeText(this@LoginActivity, "¡Bienvenido!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@LoginActivity, SolicitudActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, "Credenciales incorrectas o usuario no encontrado", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                btnLogin.isEnabled = true
                Toast.makeText(this@LoginActivity, "Error de conexión con el servidor remoto: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}