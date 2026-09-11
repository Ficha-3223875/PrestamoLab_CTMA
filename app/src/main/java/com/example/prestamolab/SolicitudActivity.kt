package com.example.prestamolab

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.prestamolab.api.ApiService
import com.example.prestamolab.model.SolicitudPrestamoRequest
import com.example.prestamolab.model.SolicitudPrestamoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Calendar

class SolicitudActivity : AppCompatActivity() {

    private lateinit var spItemEspacio: Spinner
    private lateinit var btnSeleccionarFecha: Button
    private lateinit var tvFechaSeleccionada: TextView
    private lateinit var btnHoraInicio: Button
    private lateinit var btnHoraFin: Button
    private lateinit var tvHorasSeleccionadas: TextView
    private lateinit var btnEnviarSolicitud: Button

    private var fechaSeleccionadaStr = ""
    private var horaInicioStr = ""
    private var horaFinStr = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solicitud)

        spItemEspacio = findViewById(R.id.spItemEspacio)
        btnSeleccionarFecha = findViewById(R.id.btnSeleccionarFecha)
        tvFechaSeleccionada = findViewById(R.id.tvFechaSeleccionada)
        btnHoraInicio = findViewById(R.id.btnHoraInicio)
        btnHoraFin = findViewById(R.id.btnHoraFin)
        tvHorasSeleccionadas = findViewById(R.id.tvHorasSeleccionadas)
        btnEnviarSolicitud = findViewById(R.id.btnEnviarSolicitud)

        // Cargar ítems/laboratorios
        val opciones = arrayOf("Seleccione un ítem/espacio", "Laboratorio Electrónica", "Laboratorio Redes", "Osciloscopio Digital", "Multímetro de Precisión")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, opciones)
        spItemEspacio.adapter = adapter

        btnSeleccionarFecha.setOnClickListener { mostrarDatePicker() }
        btnHoraInicio.setOnClickListener { mostrarTimePicker(isInicio = true) }
        btnHoraFin.setOnClickListener { mostrarTimePicker(isInicio = false) }

        btnEnviarSolicitud.setOnClickListener { procesarSolicitud() }
    }

    private fun mostrarDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            fechaSeleccionadaStr = "$selectedYear-${selectedMonth + 1}-$selectedDay"
            tvFechaSeleccionada.text = "Fecha: $fechaSeleccionadaStr"
        }, year, month, day)

        datePicker.datePicker.minDate = System.currentTimeMillis() - 1000
        datePicker.show()
    }

    private fun mostrarTimePicker(isInicio: Boolean) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePicker = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
            val horaFormateada = String.format("%02d:%02d", selectedHour, selectedMinute)
            if (isInicio) {
                horaInicioStr = horaFormateada
            } else {
                horaFinStr = horaFormateada
            }
            tvHorasSeleccionadas.text = "Horario: $horaInicioStr - $horaFinStr"
        }, hour, minute, true)

        timePicker.show()
    }

    private fun procesarSolicitud() {
        val item = spItemEspacio.selectedItem.toString()

        if (spItemEspacio.selectedItemPosition == 0 || fechaSeleccionadaStr.isEmpty() || horaInicioStr.isEmpty() || horaFinStr.isEmpty()) {
            Toast.makeText(this, "Por favor completa la fecha, horas y selección del ítem", Toast.LENGTH_SHORT).show()
            return
        }

        btnEnviarSolicitud.isEnabled = false

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.prestamolab.example.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        val request = SolicitudPrestamoRequest("estudiante@sena.edu.co", item, fechaSeleccionadaStr, horaInicioStr, horaFinStr)

        apiService.solicitarPrestamo(request).enqueue(object : Callback<SolicitudPrestamoResponse> {
            override fun onResponse(call: Call<SolicitudPrestamoResponse>, response: Response<SolicitudPrestamoResponse>) {
                btnEnviarSolicitud.isEnabled = true
                if (response.isSuccessful && response.body() != null) {
                    val comprobante = response.body()!!

                    // Pasar al comprobante (CA-03.2)
                    val intent = Intent(this@SolicitudActivity, ComprobanteActivity::class.java).apply {
                        putExtra("ID_SOLICITUD", comprobante.idSolicitud)
                        putExtra("ESTADO", comprobante.estado)
                        putExtra("ITEM", comprobante.itemOEspacio)
                        putExtra("FECHA", comprobante.fecha)
                        putExtra("HORA_INICIO", comprobante.horaInicio)
                        putExtra("HORA_FIN", comprobante.horaFin)
                    }
                    startActivity(intent)
                    finish()
                } else if (response.code() == 409) {
                    // CP-03.2: Choque de horarios / Item reservado
                    Toast.makeText(this@SolicitudActivity, "Error: El ítem o laboratorio ya está reservado en ese horario", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@SolicitudActivity, "Error al procesar la solicitud: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SolicitudPrestamoResponse>, t: Throwable) {
                btnEnviarSolicitud.isEnabled = true
                Toast.makeText(this@SolicitudActivity, "Error de red: Verifique su conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }
}