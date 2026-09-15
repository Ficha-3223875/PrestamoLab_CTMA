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

        cargarSolicitudesAdmin()
    }

    private fun cargarSolicitudesAdmin() {
        // Mock data incluyendo casos Aprobados para probar HU-09
        val mockSolicitudes = mutableListOf(
            SolicitudPendienteAdmin("SOL-201", "Carlos Gómez", "carlos@sena.edu.co", "Osciloscopio Digital", "2026-09-14 08:00", estado = "Aprobado"),
            SolicitudPendienteAdmin("SOL-202", "Ana Martínez", "ana@sena.edu.co", "Kit Robótica Arduino", "2026-09-14 08:15", estado = "Pendiente"),
            SolicitudPendienteAdmin("SOL-203", "Luis Rodríguez", "luis@sena.edu.co", "Microscopio Binocular", "2026-09-14 09:00", estado = "Aprobado")
        ).sortedBy { it.fechaHoraLlegada }.toMutableList()

        rvAdminPendientes.adapter = AdminSolicitudesAdapter(
            listaSolicitudes = mockSolicitudes,
            onAprobar = { solicitud -> ejecutarAprobacion(solicitud.idSolicitud) },
            onRechazar = { solicitud, motivo -> ejecutarRechazo(solicitud.idSolicitud, motivo) },
            onEntregar = { solicitud -> ejecutarEntrega(solicitud.idSolicitud) },
            onReasignar = { solicitud, nuevoEquipo -> ejecutarReasignacion(solicitud.idSolicitud, nuevoEquipo) }
        )

        // Integración con API remota
        lifecycleScope.launch {
            NetworkHelper.ejecutarPeticionSegura(
                context = this@AdminPendientesActivity,
                call = { RetrofitClient.instance.getSolicitudesPendientes() },
                onExito = { listaRemota ->
                    if (!listaRemota.isNullOrEmpty()) {
                        val ordenada = listaRemota.sortedBy { it.fechaHoraLlegada }.toMutableList()
                        rvAdminPendientes.adapter = AdminSolicitudesAdapter(
                            listaSolicitudes = ordenada,
                            onAprobar = { s -> ejecutarAprobacion(s.idSolicitud) },
                            onRechazar = { s, m -> ejecutarRechazo(s.idSolicitud, m) },
                            onEntregar = { s -> ejecutarEntrega(s.idSolicitud) },
                            onReasignar = { s, e -> ejecutarReasignacion(s.idSolicitud, e) }
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
                onExito = { Toast.makeText(this@AdminPendientesActivity, "Aprobación registrada", Toast.LENGTH_SHORT).show() }
            )
        }
    }

    private fun ejecutarRechazo(idSolicitud: String, motivo: String) {
        lifecycleScope.launch {
            NetworkHelper.ejecutarPeticionSegura(
                context = this@AdminPendientesActivity,
                call = { RetrofitClient.instance.rechazarSolicitud(idSolicitud, motivo) },
                onExito = { Toast.makeText(this@AdminPendientesActivity, "Rechazo registrado", Toast.LENGTH_SHORT).show() }
            )
        }
    }

    // CA-09.1 API Call
    private fun ejecutarEntrega(idSolicitud: String) {
        lifecycleScope.launch {
            NetworkHelper.ejecutarPeticionSegura(
                context = this@AdminPendientesActivity,
                call = { RetrofitClient.instance.registrarEntregaFisica(idSolicitud) },
                onExito = { Toast.makeText(this@AdminPendientesActivity, "Entrega guardada en servidor", Toast.LENGTH_SHORT).show() }
            )
        }
    }

    // CA-09.2 API Call
    private fun ejecutarReasignacion(idSolicitud: String, nuevoEquipo: String) {
        lifecycleScope.launch {
            NetworkHelper.ejecutarPeticionSegura(
                context = this@AdminPendientesActivity,
                call = { RetrofitClient.instance.reasignarEquipo(idSolicitud, nuevoEquipo) },
                onExito = { Toast.makeText(this@AdminPendientesActivity, "Reasignación guardada", Toast.LENGTH_SHORT).show() }
            )
        }
    }
}