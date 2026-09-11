package com.example.prestamolab

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ComprobanteActivity : AppCompatActivity() {

    private lateinit var tvIdSolicitud: TextView
    private lateinit var tvEstado: TextView
    private lateinit var tvDetalleItem: TextView
    private lateinit var tvDetalleHorario: TextView
    private lateinit var btnVolverInicio: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comprobante)

        tvIdSolicitud = findViewById(R.id.tvIdSolicitud)
        tvEstado = findViewById(R.id.tvEstado)
        tvDetalleItem = findViewById(R.id.tvDetalleItem)
        tvDetalleHorario = findViewById(R.id.tvDetalleHorario)
        btnVolverInicio = findViewById(R.id.btnVolverInicio)

        val idSolicitud = intent.getStringExtra("ID_SOLICITUD") ?: "SOL-0000"
        val estado = intent.getStringExtra("ESTADO") ?: "Pendiente"
        val item = intent.getStringExtra("ITEM") ?: "No asignado"
        val fecha = intent.getStringExtra("FECHA") ?: ""
        val horaInicio = intent.getStringExtra("HORA_INICIO") ?: ""
        val horaFin = intent.getStringExtra("HORA_FIN") ?: ""

        tvIdSolicitud.text = "Código de Solicitud: $idSolicitud"
        tvEstado.text = "Estado: $estado"
        tvDetalleItem.text = "Ítem/Espacio: $item"
        tvDetalleHorario.text = "Fecha: $fecha ($horaInicio - $horaFin)"

        btnVolverInicio.setOnClickListener {
            finish()
        }
    }
}