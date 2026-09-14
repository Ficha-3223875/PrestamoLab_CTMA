package com.example.prestamolab

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prestamolab.R // <-- ESTA IMPORTACIÓN CORRIGE LOS 4 ERRORES
import com.example.prestamolab.model.CatalogoItem
import com.example.prestamolab.model.PrestamoHistorial
import com.example.prestamolab.util.SessionManager

class MainActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sessionManager = SessionManager(this)

        val tvBienvenida = findViewById<TextView?>(R.id.tvBienvenida)
        val rvCatalogo = findViewById<RecyclerView?>(R.id.rvCatalogo)
        val rvHistorial = findViewById<RecyclerView?>(R.id.rvHistorial)
        val btnCerrarSesion = findViewById<Button?>(R.id.btnCerrarSesion)

        val correoUsuario = sessionManager.getCorreo()
        tvBienvenida?.text = if (correoUsuario.isNotEmpty()) "Hola, $correoUsuario" else "Bienvenido a PrestamoLab"

        rvCatalogo?.layoutManager = LinearLayoutManager(this)
        rvHistorial?.layoutManager = LinearLayoutManager(this)

        if (rvCatalogo != null) {
            cargarCatalogo(rvCatalogo)
        }

        if (rvHistorial != null) {
            cargarHistorial(rvHistorial)
        }

        btnCerrarSesion?.setOnClickListener {
            sessionManager.cerrarSesion()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun cargarCatalogo(rv: RecyclerView) {
        val equiposLaboratorio = listOf(
            CatalogoItem("1", "Microscopio Binocular", "Laboratorio Biología", "Zeiss", "Primo Star", "Aumento 1000x", true),
            CatalogoItem("2", "Osciloscopio Digital", "Electrónica", "Tektronix", "TBS1052B", "50 MHz, 2 ch", true),
            CatalogoItem("3", "Kit Robótica Arduino", "Mecatrónica", "Arduino", "Uno R3", "Incluye kit", false)
        )

        rv.adapter = CatalogoAdapter(equiposLaboratorio)
    }

    private fun cargarHistorial(rv: RecyclerView) {
        val listaHistorial = listOf(
            PrestamoHistorial("SOL-101", "Microscopio Binocular", "2026-03-01", "2026-03-05", "Aprobado"),
            PrestamoHistorial("SOL-102", "Osciloscopio Digital", "2026-03-10", "2026-03-12", "Pendiente")
        )
        rv.adapter = HistorialAdapter(listaHistorial)
    }
}