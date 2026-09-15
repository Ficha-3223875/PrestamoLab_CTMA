package com.example.prestamolab

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prestamolab.api.RetrofitClient
import com.example.prestamolab.model.RecursoInventario
import kotlinx.coroutines.launch

class InventarioActivity : AppCompatActivity() {

    private lateinit var rvInventario: RecyclerView
    private lateinit var btnAgregarRecurso: Button
    private lateinit var adapter: InventarioAdapter
    private val listaInventario = mutableListOf<RecursoInventario>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventario)

        rvInventario = findViewById(R.id.rvInventario)
        btnAgregarRecurso = findViewById(R.id.btnAgregarRecurso)
        rvInventario.layoutManager = LinearLayoutManager(this)

        btnAgregarRecurso.setOnClickListener {
            mostrarDialogoFormulario(null, -1)
        }

        cargarInventario()
    }

    private fun cargarInventario() {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.getInventario()
                if (response.isSuccessful && response.body() != null) {
                    listaInventario.clear()
                    listaInventario.addAll(response.body()!!)
                    configurarAdapter()
                } else {
                    cargarInventarioOffline()
                }
            } catch (e: Exception) {
                cargarInventarioOffline()
            }
        }
    }

    private fun cargarInventarioOffline() {
        listaInventario.clear()
        listaInventario.add(RecursoInventario("1", "EQ-101", "Osciloscopio Tektronix", "Equipo", "Disponible", true))
        listaInventario.add(RecursoInventario("2", "LAB-02", "Laboratorio de Electrónica", "Laboratorio", "Disponible", true))
        listaInventario.add(RecursoInventario("3", "EQ-102", "Microscopio Digital", "Equipo", "Mantenimiento", false))
        configurarAdapter()
    }

    private fun configurarAdapter() {
        adapter = InventarioAdapter(
            listaInventario,
            onEditar = { recurso, pos -> mostrarDialogoFormulario(recurso, pos) },
            onEliminar = { recurso, pos -> confirmarEliminacion(recurso, pos) }
        )
        rvInventario.adapter = adapter
    }

    private fun mostrarDialogoFormulario(recurso: RecursoInventario?, position: Int) {
        val view = layoutInflater.inflate(R.layout.dialog_crear_editar_recurso, null)
        val etCodigo = view.findViewById<EditText>(R.id.etCodigo)
        val etNombre = view.findViewById<EditText>(R.id.etNombre)
        val etTipo = view.findViewById<EditText>(R.id.etTipo)
        val etEstado = view.findViewById<EditText>(R.id.etEstado)

        val esEdicion = recurso != null
        if (esEdicion) {
            etCodigo.setText(recurso!!.codigo)
            etNombre.setText(recurso.nombre)
            etTipo.setText(recurso.tipo)
            etEstado.setText(recurso.estado)
        }

        AlertDialog.Builder(this)
            .setTitle(if (esEdicion) "Editar Recurso" else "Crear Recurso")
            .setView(view)
            .setPositiveButton("Guardar") { dialog, _ ->
                val nuevoRecurso = RecursoInventario(
                    id = recurso?.id ?: System.currentTimeMillis().toString(),
                    codigo = etCodigo.text.toString().trim(),
                    nombre = etNombre.text.toString().trim(),
                    tipo = etTipo.text.toString().trim(),
                    estado = etEstado.text.toString().trim(),
                    disponibilidad = etEstado.text.toString().trim().equals("Disponible", ignoreCase = true)
                )

                if (esEdicion) {
                    guardarCambiosAPI(nuevoRecurso, position)
                } else {
                    crearRecursoAPI(nuevoRecurso)
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun crearRecursoAPI(recurso: RecursoInventario) {
        lifecycleScope.launch {
            try {
                val res = RetrofitClient.instance.crearRecurso(recurso)
                listaInventario.add(recurso)
                adapter.notifyItemInserted(listaInventario.size - 1)
                Toast.makeText(this@InventarioActivity, "Recurso creado con éxito", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                listaInventario.add(recurso)
                adapter.notifyItemInserted(listaInventario.size - 1)
                Toast.makeText(this@InventarioActivity, "Guardado localmente (Offline)", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun guardarCambiosAPI(recurso: RecursoInventario, position: Int) {
        lifecycleScope.launch {
            try {
                RetrofitClient.instance.actualizarRecurso(recurso.id, recurso)
                listaInventario[position] = recurso
                adapter.notifyItemChanged(position)
                Toast.makeText(this@InventarioActivity, "Recurso actualizado", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                listaInventario[position] = recurso
                adapter.notifyItemChanged(position)
                Toast.makeText(this@InventarioActivity, "Actualizado localmente (Offline)", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun confirmarEliminacion(recurso: RecursoInventario, position: Int) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar Recurso")
            .setMessage("¿Estás seguro de eliminar '${recurso.nombre}'? Prevendrá la eliminación si está asociado a préstamos activos.")
            .setPositiveButton("Eliminar") { dialog, _ ->
                eliminarRecursoAPI(recurso.id, position)
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun eliminarRecursoAPI(id: String, position: Int) {
        lifecycleScope.launch {
            try {
                RetrofitClient.instance.eliminarRecurso(id)
                listaInventario.removeAt(position)
                adapter.notifyItemRemoved(position)
                Toast.makeText(this@InventarioActivity, "Recurso eliminado del inventario", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                listaInventario.removeAt(position)
                adapter.notifyItemRemoved(position)
                Toast.makeText(this@InventarioActivity, "Eliminado localmente (Offline)", Toast.LENGTH_SHORT).show()
            }
        }
    }
}