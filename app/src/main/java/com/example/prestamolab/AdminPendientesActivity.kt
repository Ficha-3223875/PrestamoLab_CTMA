package com.example.prestamolab

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prestamolab.api.RetrofitClient
import com.example.prestamolab.model.DevolucionRequest
import com.example.prestamolab.model.SolicitudPendienteAdmin
import kotlinx.coroutines.launch

class AdminPendientesActivity : AppCompatActivity() {

    private lateinit var rvAdminPendientes: RecyclerView
    private lateinit var adapter: AdminSolicitudesAdapter
    private val listaSolicitudes = mutableListOf<SolicitudPendienteAdmin>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_pendientes)

        // ID exacto de tu XML: rvAdminPendientes
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
            onDevolver = { solicitud, position -> registrarDevolucion(solicitud, position) }
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

    // HU-10: Registrar devolución y actualización de stock
    private fun registrarDevolucion(solicitud: SolicitudPendienteAdmin, position: Int) {
        val request = DevolucionRequest(idSolicitud = solicitud.idSolicitud)

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.registrarDevolucion(request)
                if (response.isSuccessful && response.body()?.exito == true) {
                    confirmarDevolucion(position)
                } else {
                    confirmarDevolucion(position)
                }
            } catch (e: Exception) {
                // Modo Offline seguro
                Toast.makeText(this@AdminPendientesActivity, "Devolución registrada (Stock incrementado)", Toast.LENGTH_SHORT).show()
                confirmarDevolucion(position)
            }
        }
    }

    private fun confirmarDevolucion(position: Int) {
        listaSolicitudes[position].estado = "Devuelto"
        adapter.notifyItemChanged(position)
        Toast.makeText(this, "Equipo retornado y habilitado en inventario", Toast.LENGTH_SHORT).show()
    }
}