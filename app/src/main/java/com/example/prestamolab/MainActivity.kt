package com.example.prestamolab

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prestamolab.api.ApiClient
import com.example.prestamolab.model.CatalogoItem
import com.example.prestamolab.model.PrestamoHistorial
import com.example.prestamolab.util.NetworkHelper
import com.example.prestamolab.util.SessionManager
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sessionManager = SessionManager(this)

        val tvBienvenida = findViewById<TextView>(R.id.tvBienvenida)
        val rvCatalogo = findViewById<RecyclerView>(R.id.rvCatalogo)
        val rvHistorial = findViewById<RecyclerView>(R.id.rvHistorial)
        val btnCerrarSesion = findViewById<Button>(R.id.btnCerrarSesion)

        val correoUsuario = sessionManager.getCorreo()
        tvBienvenida?.text = if (correoUsuario.isNotEmpty()) "Hola, $correoUsuario" else "Bienvenido a PrestamoLab"

        // Configuración de LayoutManagers para las listas
        rvCatalogo?.layoutManager = LinearLayoutManager(this)
        rvHistorial?.layoutManager = LinearLayoutManager(this)

        // Carga de catálogo con características completas
        if (rvCatalogo != null) {
            cargarCatalogo(rvCatalogo)
        }

        // Carga del historial de préstamos (HU-04)
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
            CatalogoItem(
                id = "1",
                nombre = "Microscopio Binocular",
                categoria = "Laboratorio Biología",
                marca = "Zeiss",
                modelo = "Primo Star",
                caracteristicas = "Aumento 1000x, iluminación LED, platina mecánica de precisión.",
                disponible = true
            ),
            CatalogoItem(
                id = "2",
                nombre = "Osciloscopio Digital",
                categoria = "Electrónica",
                marca = "Tektronix",
                modelo = "TBS1052B",
                caracteristicas = "2 Canales, 50 MHz de ancho de banda, pantalla a color 7 pulgadas.",
                disponible = true
            ),
            CatalogoItem(
                id = "3",
                nombre = "Kit de Robótica Arduino",
                categoria = "Mecatrónica",
                marca = "Arduino",
                modelo = "Starter Kit v3",
                caracteristicas = "Incluye placa Uno R3, servomotores, pantalla LCD, sensores y cableado.",
                disponible = false
            ),
            CatalogoItem(
                id = "4",
                nombre = "Multímetro Digital Pro",
                categoria = "Medición",
                marca = "Fluke",
                modelo = "117 Electrician's",
                caracteristicas = "Medición True-RMS, detección de voltaje sin contacto VolTect.",
                disponible = true
            )
        )

        rv.adapter = CatalogoAdapter(equiposLaboratorio)

        // Intento de consumo remoto seguro con ApiService (HU-05)
        lifecycleScope.launch {
            NetworkHelper.ejecutarPeticionSegura(
                context = this@MainActivity,
                call = { ApiClient.apiService.getCatalogo() },
                onExito = { respuesta ->
                    // Si el backend remoto responde, se actualiza la lista
                }
            )
        }
    }

    private fun cargarHistorial(rv: RecyclerView) {
        val listaHistorial = listOf(
            PrestamoHistorial("SOL-101", "Microscopio Binocular", "2026-03-01", "2026-03-05", "Aprobado"),
            PrestamoHistorial("SOL-102", "Osciloscopio Digital", "2026-03-10", "2026-03-12", "Pendiente")
        )
        rv.adapter = HistorialAdapter(listaHistorial)
    }
}