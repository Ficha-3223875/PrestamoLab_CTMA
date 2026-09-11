package com.example.prestamolab

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.prestamolab.util.SessionManager

class LoginActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sessionManager = SessionManager(this)

        if (sessionManager.isLoggedIn()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_login)

        // TUS IDS EXACTOS DEL XML:
        val etCorreo = findViewById<EditText>(R.id.etCorreoLogin)
        val etPassword = findViewById<EditText>(R.id.etContrasenaLogin)
        val btnIngresar = findViewById<Button>(R.id.btnLogin)
        val tvRegistrarse = findViewById<TextView>(R.id.tvIrARegistro)

        btnIngresar.setOnClickListener {
            val correo = etCorreo.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (correo.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor llena todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validar localmente evitando la llamada fallida al servidor 10.0.2.2
            if (sessionManager.validarUsuarioLocal(correo, password)) {
                sessionManager.guardarSesion("token_local", correo)
                Toast.makeText(this, "Bienvenido $correo", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
            }
        }

        tvRegistrarse.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
    }
}