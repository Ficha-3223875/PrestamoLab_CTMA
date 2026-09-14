package com.example.prestamolab

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prestamolab.api.RetrofitClient
import com.example.prestamolab.model.SolicitudPendienteAdmin
import com.example.prestamolab.util.NetworkHelper
import kotlinx.coroutines.launch

class AdminPendientesActivity : AppCompatActivity() {

    private lateinit var rvAdminPendientes: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_pendientes)

        rvAdminPendientes = findViewById(R.id.rvAdminPendientes)
        rvAdminPendientes.layoutManager = LinearLayoutManager(this)

        cargarSolicitudesPendientes()
    }

    private fun cargarSolicitudesPendientes() {
        // Datos mock para pruebas en orden cronológico (CA-08.1)
        val mockSolicitudes = mutableListOf(
            SolicitudPendienteAdmin("SOL-201", "Carlos Gómez", "carlos@sena.edu.co", "Osciloscopio Digital", "2026-09-14 08:00"),
            SolicitudPendienteAdmin("SOL-202", "Ana Martínez", "ana@sena.edu.co", "Kit Robótica Arduino", "2026-09-14 08:15"),
            SolicitudPendienteAdmin("SOL-203", "Luis Rodríguez", "luis@sena.edu.co", "Microscopio Binocular", "2026-09-14 09:00")
        ).sortedBy { it.fechaHoraLlegada }.toMutableList() // Orden cronológico (CA-08.1)

        rvAdminPendientes.adapter = AdminSolicitudesAdapter(
            listaSolicitudes = mockSolicitudes,
            onAprobar = { solicitud ->
                ejecutarAprobacion(solicitud.idSolicitud)
            },
            onRechazar = { solicitud, motivo ->
                ejecutarRechazo(solicitud.idSolicitud, motivo)
            }
        )

        // Carga desde el servidor si la API responde
        lifecycleScope.launch {
            NetworkHelper.ejecutarPeticionSegura(
                context = this@AdminPendientesActivity,
                call = { RetrofitClient.instance.getSolicitudesPendientes() },
                onExito = { listaRemota ->
                    if (!listaRemota.isNullOrEmpty()) {
                        val ordenada = listaRemota.sortedBy { it.fechaHoraLlegada }.toMutableList()
                        rvAdminPendientes.adapter = AdminSolicitudesAdapter(
                            listaSolicitudes = ordenada,
                            onAprobar = { solicitud -> ejecutarAprobacion(solicitud.idSolicitud) },
                            onRechazar = { solicitud, motivo -> ejecutarRechazo(solicitud.idSolicitud, motivo) }
                        )
                    }
                }
            )
        }
    }

    private fun ejecutarAprobacion(idSolicitud: String) {
        lifecycleScope.launch {
            NetworkHelper.ejecutarPeticionSegura(
                context = this@AdminPendientesActivity,
                call = { RetrofitClient.instance.aprobarSolicitud(idSolicitud) },
                onExito = {
                    Toast.makeText(this@AdminPendientesActivity, "Aprobada en backend", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    private fun ejecutarRechazo(idSolicitud: String, motivo: String) {
        lifecycleScope.launch {
            NetworkHelper.ejecutarPeticionSegura(
                context = this@AdminPendientesActivity,
                call = { RetrofitClient.instance.rechazarSolicitud(idSolicitud, motivo) },
                onExito = {
                    Toast.makeText(this@AdminPendientesActivity, "Rechazada en backend", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}