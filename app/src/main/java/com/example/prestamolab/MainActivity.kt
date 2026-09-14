package com.example.prestamolab

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prestamolab.R
import com.example.prestamolab.api.RetrofitClient
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
        // Datos locales de respaldo con sus características completas
        val equiposLaboratorio = listOf(
            CatalogoItem("1", "Microscopio Binocular", "Laboratorio Biología", "Zeiss", "Primo Star", "Aumento 1000x, iluminación LED, platina mecánica de precisión.", true),
            CatalogoItem("2", "Osciloscopio Digital", "Electrónica", "Tektronix", "TBS1052B", "2 Canales, 50 MHz de ancho de banda, pantalla a color 7 pulgadas.", true),
            CatalogoItem("3", "Kit Robótica Arduino", "Mecatrónica", "Arduino", "Uno R3", "Incluye placa Uno R3, servomotores, pantalla LCD, sensores y cableado.", false),
            CatalogoItem("4", "Multímetro Digital Pro", "Medición", "Fluke", "117 Electrician's", "Medición True-RMS, detección de voltaje sin contacto VolTect.", true)
        )

        rv.adapter = CatalogoAdapter(equiposLaboratorio)

        // HU-05: Petición remota con manejo de errores y fallos de conexión sin crash
        lifecycleScope.launch {
            NetworkHelper.ejecutarPeticionSegura(
                context = this@MainActivity,
                call = { RetrofitClient.instance.getCatalogo() },
                onExito = { respuesta ->
                    if (!respuesta.isNullOrEmpty()) {
                        rv.adapter = CatalogoAdapter(respuesta)
                    }
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

        // HU-05: Petición remota del historial
        lifecycleScope.launch {
            NetworkHelper.ejecutarPeticionSegura(
                context = this@MainActivity,
                call = { RetrofitClient.instance.getHistorial() },
                onExito = { respuesta ->
                    if (!respuesta.isNullOrEmpty()) {
                        rv.adapter = HistorialAdapter(respuesta)
                    }
                }
            )
        }
    }
}