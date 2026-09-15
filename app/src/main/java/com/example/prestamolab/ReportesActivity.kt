package com.example.prestamolab

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.prestamolab.api.RetrofitClient
import com.example.prestamolab.model.ReporteResumen
import kotlinx.coroutines.launch

class ReportesActivity : AppCompatActivity() {

    private lateinit var etFechaInicio: EditText
    private lateinit var etFechaFin: EditText
    private lateinit var etFicha: EditText
    private lateinit var btnGenerarReporte: Button
    private lateinit var tvTotalPrestamos: TextView
    private lateinit var tvDevolucionesATiempo: TextView
    private lateinit var tvIncidencias: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reportes)

        etFechaInicio = findViewById(R.id.etFechaInicio)
        etFechaFin = findViewById(R.id.etFechaFin)
        etFicha = findViewById(R.id.etFicha)
        btnGenerarReporte = findViewById(R.id.btnGenerarReporte)
        tvTotalPrestamos = findViewById(R.id.tvTotalPrestamos)
        tvDevolucionesATiempo = findViewById(R.id.tvDevolucionesATiempo)
        tvIncidencias = findViewById(R.id.tvIncidencias)

        btnGenerarReporte.setOnClickListener {
            val fechaInicio = etFechaInicio.text.toString().trim()
            val fechaFin = etFechaFin.text.toString().trim()
            val ficha = etFicha.text.toString().trim()

            consultarReporte(fechaInicio, fechaFin, ficha)
        }
    }

    private fun consultarReporte(fechaInicio: String, fechaFin: String, ficha: String) {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.obtenerReporteResumen(
                    fechaInicio = fechaInicio.ifBlank { null },
                    fechaFin = fechaFin.ifBlank { null },
                    ficha = ficha.ifBlank { null }
                )
                if (response.isSuccessful) {
                    val reporte = response.body() ?: ReporteResumen()
                    tvTotalPrestamos.text = reporte.totalPrestamos.toString()
                    tvDevolucionesATiempo.text = reporte.devolucionesATiempo.toString()
                    tvIncidencias.text = reporte.incidenciasReportadas.toString()
                    Toast.makeText(this@ReportesActivity, "Reporte generado correctamente", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@ReportesActivity, "Error al obtener reporte", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@ReportesActivity, "Error de red o servidor", Toast.LENGTH_SHORT).show()
            }
        }
    }
}