package com.example.prestamolab

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prestamolab.model.ItemCatalogo
import com.example.prestamolab.model.PrestamoHistorial

class HistorialActivity : AppCompatActivity() {

    private lateinit var rvCatalogoHorizontal: RecyclerView
    private lateinit var rvHistorial: RecyclerView
    private lateinit var etBuscarCatalogo: EditText
    private lateinit var btnNuevaSolicitud: Button

    private lateinit var catalogoAdapter: CatalogoAdapter

    private val listaCatalogo = listOf(
        ItemCatalogo("1", "Laboratorio Electrónica", "Espacio", "Disponible"),
        ItemCatalogo("2", "Osciloscopio Digital", "Equipo", "Disponible"),
        ItemCatalogo("3", "Laboratorio Redes", "Espacio", "Prestado"),
        ItemCatalogo("4", "Multímetro Fluke", "Equipo", "Disponible"),
        ItemCatalogo("5", "Impresora 3D", "Equipo", "Mantenimiento")
    )

    private val listaHistorial = listOf(
        PrestamoHistorial("SOL-1024", "Osciloscopio Digital Tektronix", "2026-09-01", "2026-09-03", "Devuelto"),
        PrestamoHistorial("SOL-1088", "Laboratorio de Redes (Mesa 4)", "2026-09-10", "2026-09-11", "Aprobado"),
        PrestamoHistorial("SOL-1102", "Multímetro Fluke 87V", "2026-09-11", "2026-09-12", "Pendiente")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial)

        rvCatalogoHorizontal = findViewById(R.id.rvCatalogoHorizontal)
        rvHistorial = findViewById(R.id.rvHistorial)
        etBuscarCatalogo = findViewById(R.id.etBuscarCatalogo)
        btnNuevaSolicitud = findViewById(R.id.btnNuevaSolicitud)

        // Configuración Catálogo Horizontal
        rvCatalogoHorizontal.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        catalogoAdapter = CatalogoAdapter(listaCatalogo) { item ->
            if (item.disponibilidad == "Disponible") {
                startActivity(Intent(this, SolicitudActivity::class.java))
            } else {
                Toast.makeText(this, "El item no está disponible", Toast.LENGTH_SHORT).show()
            }
        }
        rvCatalogoHorizontal.adapter = catalogoAdapter

        // Buscador del Catálogo
        etBuscarCatalogo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val texto = s.toString()
                val filtrada = listaCatalogo.filter {
                    it.nombre.contains(texto, ignoreCase = true) || it.categoria.contains(texto, ignoreCase = true)
                }
                catalogoAdapter.actualizarLista(filtrada)
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Configuración Historial
        rvHistorial.layoutManager = LinearLayoutManager(this)
        rvHistorial.adapter = HistorialAdapter(listaHistorial)

        btnNuevaSolicitud.setOnClickListener {
            startActivity(Intent(this, SolicitudActivity::class.java))
        }
    }
}