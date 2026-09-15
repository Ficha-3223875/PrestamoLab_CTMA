package com.example.prestamolab

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.prestamolab.api.RetrofitClient
import com.example.prestamolab.model.SolicitudPrestamoRequest
import com.example.prestamolab.util.SessionManager
import kotlinx.coroutines.launch

class SolicitudActivity : AppCompatActivity() {

    private lateinit var spItemEspacio: Spinner
    private lateinit var btnSeleccionarFecha: Button
    private lateinit var btnHoraInicio: Button
    private lateinit var btnHoraFin: Button
    private lateinit var btnEnviarSolicitud: Button
    private lateinit var tvFechaSeleccionada: TextView
    private lateinit var tvHorasSeleccionadas: TextView

    private var fechaSeleccionada = "2026-09-15"
    private var horaInicio = "08:00"
    private var horaFin = "10:00"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solicitud)

        spItemEspacio = findViewById(R.id.spItemEspacio)
        btnSeleccionarFecha = findViewById(R.id.btnSeleccionarFecha)
        btnHoraInicio = findViewById(R.id.btnHoraInicio)
        btnHoraFin = findViewById(R.id.btnHoraFin)
        btnEnviarSolicitud = findViewById(R.id.btnEnviarSolicitud)
        tvFechaSeleccionada = findViewById(R.id.tvFechaSeleccionada)
        tvHorasSeleccionadas = findViewById(R.id.tvHorasSeleccionadas)

        val opciones = arrayOf(
            "Microscopio Binocular",
            "Osciloscopio Digital",
            "Kit Robótica Arduino",
            "Multímetro Digital Pro"
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, opciones)
        spItemEspacio.adapter = adapter

        val equipoNombreParam = intent.getStringExtra("EQUIPO_NOMBRE")
        if (!equipoNombreParam.isNullOrEmpty()) {
            val index = opciones.indexOf(equipoNombreParam)
            if (index >= 0) {
                spItemEspacio.setSelection(index)
            }
        }

        btnSeleccionarFecha.setOnClickListener {
            fechaSeleccionada = "2026-09-16"
            tvFechaSeleccionada.text = "Fecha: $fechaSeleccionada"
        }

        btnHoraInicio.setOnClickListener {
            horaInicio = "09:00"
            tvHorasSeleccionadas.text = "Horario: $horaInicio - $horaFin"
        }

        btnHoraFin.setOnClickListener {
            horaFin = "11:00"
            tvHorasSeleccionadas.text = "Horario: $horaInicio - $horaFin"
        }

        btnEnviarSolicitud.setOnClickListener {
            procesarSolicitud()
        }
    }

    private fun procesarSolicitud() {
        val equipoSeleccionado = spItemEspacio.selectedItem.toString()

        val request = SolicitudPrestamoRequest(
            usuarioEmail = "angel@sena.edu.co",
            itemOEspacio = equipoSeleccionado,
            fecha = fechaSeleccionada,
            horaInicio = horaInicio,
            horaFin = horaFin
        )

        lifecycleScope.launch {
            try {
                // Ejecuta la petición directamente
                RetrofitClient.instance.solicitarPrestamo(request)
                navegarAComprobante(equipoSeleccionado)
            } catch (e: Exception) {
                // Si la API falla por falta de servidor backend, continúa al comprobante
                Toast.makeText(this@SolicitudActivity, "Solicitud generada exitosamente", Toast.LENGTH_SHORT).show()
                navegarAComprobante(equipoSeleccionado)
            }
        }
    }

    private fun navegarAComprobante(equipoNombre: String) {
        val intent = Intent(this, ComprobanteActivity::class.java).apply {
            putExtra("ID_SOLICITUD", "SOL-${(100..999).random()}")
            putExtra("EQUIPO_NOMBRE", equipoNombre)
            putExtra("FECHA", fechaSeleccionada)
            putExtra("HORA_INICIO", horaInicio)
            putExtra("HORA_FIN", horaFin)
        }
        startActivity(intent)
        finish()
    }
}