package com.example.prestamolab

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prestamolab.R
import com.example.prestamolab.model.PrestamoHistorial

class HistorialActivity : AppCompatActivity() {

    private lateinit var rvHistorial: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial)

        rvHistorial = findViewById(R.id.rvHistorial)
        rvHistorial.layoutManager = LinearLayoutManager(this)

        cargarHistorial()
    }

    private fun cargarHistorial() {
        val listaHistorial = listOf(
            PrestamoHistorial("SOL-101", "Microscopio Binocular", "2026-03-01", "2026-03-05", "Aprobado"),
            PrestamoHistorial("SOL-102", "Osciloscopio Digital", "2026-03-10", "2026-03-12", "Pendiente")
        )
        rvHistorial.adapter = HistorialAdapter(listaHistorial)
    }
}