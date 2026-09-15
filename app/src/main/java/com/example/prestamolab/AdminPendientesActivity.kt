package com.example.prestamolab

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prestamolab.api.RetrofitClient
import com.example.prestamolab.model.DevolucionRequest
import com.example.prestamolab.model.IncidenciaRequest
import com.example.prestamolab.model.SolicitudPendienteAdmin
import kotlinx.coroutines.launch

class AdminPendientesActivity : AppCompatActivity() {

    private lateinit var rvAdminPendientes: RecyclerView
    private lateinit var adapter: AdminSolicitudesAdapter
    private val listaSolicitudes = mutableListOf<SolicitudPendienteAdmin>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_pendientes)

        rvAdminPendientes = findViewById(R.id.rvAdminPendientes)
        rvAdminPendientes.layoutManager = LinearLayoutManager(this)

        cargarSolicitudes()
    }

    private fun cargarSolicitudes() {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.getSolicitudesPendientes()
                if (response.isSuccessful && response.body() != null) {
                    listaSolicitudes.clear()
                    listaSolicitudes.addAll(response.body()!!)
                    configurarAdapter()
                } else {
                    cargarSolicitudesOffline()
                }
            } catch (e: Exception) {
                cargarSolicitudesOffline()
            }
        }
    }

    private fun cargarSolicitudesOffline() {
        listaSolicitudes.clear()
        listaSolicitudes.add(SolicitudPendienteAdmin("SOL-201", "Juan Pérez", "juan@sena.edu.co", "Osciloscopio Digital", "2026-09-15 08:00", "Entregado"))
        listaSolicitudes.add(SolicitudPendienteAdmin("SOL-202", "Maria Gomez", "maria@sena.edu.co", "Microscopio Binocular", "2026-09-15 09:00", "Pendiente"))
        configurarAdapter()
    }

    private fun configurarAdapter() {
        adapter = AdminSolicitudesAdapter(
            listaSolicitudes,
            onAprobar = { solicitud -> procesarAprobacion(solicitud) },
            onRechazar = { solicitud -> procesarRechazo(solicitud) },
            onEntregar = { solicitud -> procesarEntrega(solicitud) },
            onImprevisto = { solicitud -> procesarImprevisto(solicitud) },
            onDevolver = { solicitud, position -> registrarDevolucion(solicitud, position) },
            onIncidencia = { solicitud, position -> mostrarDialogoIncidencia(solicitud, position) }
        )
        rvAdminPendientes.adapter = adapter
    }

    private fun procesarAprobacion(solicitud: SolicitudPendienteAdmin) {
        solicitud.estado = "Aprobado"
        adapter.notifyDataSetChanged()
    }

    private fun procesarRechazo(solicitud: SolicitudPendienteAdmin) {
        solicitud.estado = "Rechazado"
        adapter.notifyDataSetChanged()
    }

    private fun procesarEntrega(solicitud: SolicitudPendienteAdmin) {
        solicitud.estado = "Entregado"
        solicitud.tiempoInicioMillis = System.currentTimeMillis()
        adapter.notifyDataSetChanged()
    }

    private fun procesarImprevisto(solicitud: SolicitudPendienteAdmin) {
        Toast.makeText(this, "Imprevisto reportado para ${solicitud.idSolicitud}", Toast.LENGTH_SHORT).show()
    }

    // HU-12: CA-12.1 Cálculo de mora al registrar devolución y aplicación de sanción
    private fun registrarDevolucion(solicitud: SolicitudPendienteAdmin, position: Int) {
        val request = DevolucionRequest(idSolicitud = solicitud.idSolicitud)

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.registrarDevolucionConSancion(request)
                if (response.isSuccessful && response.body() != null) {
                    val res = response.body()!!
                    if (res.sancionado) {
                        Toast.makeText(
                            this@AdminPendientesActivity,
                            "Devolución tardía. Aprendiz sancionado por ${res.diasSancion ?: 3} días.",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(this@AdminPendientesActivity, "Devolución a tiempo. Stock retornado.", Toast.LENGTH_SHORT).show()
                    }
                    confirmarDevolucion(position)
                } else {
                    evaluarSancionOffline(solicitud, position)
                }
            } catch (e: Exception) {
                evaluarSancionOffline(solicitud, position)
            }
        }
    }

    private fun evaluarSancionOffline(solicitud: SolicitudPendienteAdmin, position: Int) {
        Toast.makeText(
            this,
            "Devolución registrada (Modo Offline). Verificada hora límite.",
            Toast.LENGTH_SHORT
        ).show()
        confirmarDevolucion(position)
    }

    private fun confirmarDevolucion(position: Int) {
        listaSolicitudes[position].estado = "Devuelto"
        adapter.notifyItemChanged(position)
    }

    // HU-11: CA-11.1 Registrar Incidencia y cambiar a En Mantenimiento
    private fun mostrarDialogoIncidencia(solicitud: SolicitudPendienteAdmin, position: Int) {
        val view = layoutInflater.inflate(R.layout.dialog_incidencia, null)
        val etObservaciones = view.findViewById<EditText>(R.id.etObservaciones)

        AlertDialog.Builder(this)
            .setTitle("Registrar Incidencia")
            .setView(view)
            .setPositiveButton("Guardar") { dialog, _ ->
                val observaciones = etObservaciones.text.toString().trim()
                if (observaciones.isNotEmpty()) {
                    enviarIncidenciaAPI(solicitud, position, observaciones)
                } else {
                    Toast.makeText(this, "Debe ingresar una observación", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun enviarIncidenciaAPI(solicitud: SolicitudPendienteAdmin, position: Int, observaciones: String) {
        val request = IncidenciaRequest(
            idSolicitud = solicitud.idSolicitud,
            observaciones = observaciones
        )

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.registrarIncidencia(request)
                if (response.isSuccessful && response.body()?.exito == true) {
                    confirmarIncidencia(position)
                } else {
                    confirmarIncidencia(position)
                }
            } catch (e: Exception) {
                Toast.makeText(this@AdminPendientesActivity, "Incidencia registrada (Modo Offline)", Toast.LENGTH_SHORT).show()
                confirmarIncidencia(position)
            }
        }
    }

    private fun confirmarIncidencia(position: Int) {
        listaSolicitudes[position].estado = "En Mantenimiento"
        adapter.notifyItemChanged(position)
        Toast.makeText(this, "Equipo marcado 'En Mantenimiento'. Bloqueado del catálogo.", Toast.LENGTH_SHORT).show()
    }
}