package com.example.prestamolab

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.prestamolab.api.RetrofitClient
import com.example.prestamolab.util.SessionManager
import kotlinx.coroutines.launch

class SolicitudActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solicitud)

        sessionManager = SessionManager(this)

        // CA-12.2: Verificar si el aprendiz está sancionado antes de permitir la reserva
        verificarSancionUsuario()
    }

    private fun verificarSancionUsuario() {
        val email = sessionManager.getUserEmail() ?: "aprendiz@sena.edu.co"

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.verificarEstadoUsuario(email)
                if (response.isSuccessful && response.body()?.sancionado == true) {
                    val fechaFin = response.body()?.fechaFinSancion ?: "3 días"
                    bloquearPorSancion(fechaFin)
                }
            } catch (e: Exception) {
                // Modo Offline: Si existe registro de sanción local
                if (sessionManager.isUsuarioSancionado()) {
                    val fechaFin = sessionManager.getFechaFinSancion() ?: "2026-09-18"
                    bloquearPorSancion(fechaFin)
                }
            }
        }
    }

    private fun bloquearPorSancion(fechaFin: String) {
        AlertDialog.Builder(this)
            .setTitle("Usuario Sancionado")
            .setMessage("Posees una sanción activa por entrega tardía. No puedes realizar reservas hasta la fecha: $fechaFin.")
            .setCancelable(false)
            .setPositiveButton("Entendido") { dialog, _ ->
                dialog.dismiss()
                finish() // Cierra la pantalla para evitar que realice la solicitud
            }
            .show()
    }
}