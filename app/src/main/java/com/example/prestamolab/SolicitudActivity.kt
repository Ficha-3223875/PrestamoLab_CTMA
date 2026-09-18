package com.example.prestamolab

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.prestamolab.api.RetrofitClient
import com.example.prestamolab.util.SessionManager
import kotlinx.coroutines.launch
import java.util.Calendar

class SolicitudActivity : AppCompatActivity() {

    // 1. Declaración global a nivel de clase (AFUERA de onCreate)
    private lateinit var sessionManager: SessionManager
    private lateinit var spItemEspacio: Spinner
    private lateinit var btnSeleccionarFecha: Button
    private lateinit var tvFechaSeleccionada: TextView
    private lateinit var btnHoraInicio: Button
    private lateinit var btnHoraFin: Button
    private lateinit var tvHorasSeleccionadas: TextView
    private lateinit var btnEnviarSolicitud: Button

    private var horaInicioStr = ""
    private var horaFinStr = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solicitud)

        sessionManager = SessionManager(this)

        // Inicializamos vistas y selectores
        inicializarVistas()
        configurarSelectores()

        btnEnviarSolicitud.setOnClickListener {
            validarYEnviarSolicitud()
        }

        verificarSancionUsuario()
    }

    private fun inicializarVistas() {
        spItemEspacio = findViewById(R.id.spItemEspacio)
        btnSeleccionarFecha = findViewById(R.id.btnSeleccionarFecha)
        tvFechaSeleccionada = findViewById(R.id.tvFechaSeleccionada)
        btnHoraInicio = findViewById(R.id.btnHoraInicio)
        btnHoraFin = findViewById(R.id.btnHoraFin)
        tvHorasSeleccionadas = findViewById(R.id.tvHorasSeleccionadas)
        btnEnviarSolicitud = findViewById(R.id.btnEnviarSolicitud)
    }

    private fun configurarSelectores() {
        val calendar = Calendar.getInstance()

        btnSeleccionarFecha.setOnClickListener {
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val fechaFormateada = String.format("%02d/%02d/%d", selectedDay, selectedMonth + 1, selectedYear)
                tvFechaSeleccionada.text = fechaFormateada
            }, year, month, day).show()
        }

        btnHoraInicio.setOnClickListener {
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            TimePickerDialog(this, { _, selectedHour, selectedMinute ->
                horaInicioStr = String.format("%02d:%02d", selectedHour, selectedMinute)
                actualizarTextoHorario()
            }, hour, minute, true).show()
        }

        btnHoraFin.setOnClickListener {
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            TimePickerDialog(this, { _, selectedHour, selectedMinute ->
                horaFinStr = String.format("%02d:%02d", selectedHour, selectedMinute)
                actualizarTextoHorario()
            }, hour, minute, true).show()
        }
    }

    private fun actualizarTextoHorario() {
        if (horaInicioStr.isNotEmpty() && horaFinStr.isNotEmpty()) {
            tvHorasSeleccionadas.text = "De $horaInicioStr a $horaFinStr"
        } else if (horaInicioStr.isNotEmpty()) {
            tvHorasSeleccionadas.text = "Inicio: $horaInicioStr"
        } else if (horaFinStr.isNotEmpty()) {
            tvHorasSeleccionadas.text = "Fin: $horaFinStr"
        }
    }

    private fun validarYEnviarSolicitud() {
        val fecha = tvFechaSeleccionada.text.toString()
        if (fecha == "Fecha no seleccionada" || fecha.isEmpty()) {
            Toast.makeText(this, "Por favor seleccione una fecha", Toast.LENGTH_SHORT).show()
            return
        }

        if (horaInicioStr.isEmpty() || horaFinStr.isEmpty()) {
            Toast.makeText(this, "Por favor seleccione la hora de inicio y fin", Toast.LENGTH_SHORT).show()
            return
        }

        Toast.makeText(this, "¡Solicitud registrada con éxito!", Toast.LENGTH_SHORT).show()
        finish()
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
                finish()
            }
            .show()
    }
}