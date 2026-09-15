package com.example.prestamolab

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.prestamolab.model.SolicitudPendienteAdmin
import java.util.Locale

class AdminSolicitudesAdapter(
    private var listaSolicitudes: MutableList<SolicitudPendienteAdmin>,
    private val onAprobar: (SolicitudPendienteAdmin) -> Unit,
    private val onRechazar: (SolicitudPendienteAdmin, String) -> Unit,
    private val onEntregar: (SolicitudPendienteAdmin) -> Unit,
    private val onReasignar: (SolicitudPendienteAdmin, String) -> Unit
) : RecyclerView.Adapter<AdminSolicitudesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvIdSolicitud: TextView = view.findViewById(R.id.tvIdSolicitudAdmin)
        val tvEstado: TextView = view.findViewById(R.id.tvEstadoAdmin)
        val tvAprendizInfo: TextView = view.findViewById(R.id.tvAprendizInfo)
        val tvEquipoSolicitado: TextView = view.findViewById(R.id.tvEquipoSolicitado)
        val tvTemporizador: TextView = view.findViewById(R.id.tvTemporizador)
        val btnAprobar: Button = view.findViewById(R.id.btnAprobarAdmin)
        val btnRechazar: Button = view.findViewById(R.id.btnRechazarAdmin)
        val btnEntregar: Button = view.findViewById(R.id.btnEntregarAdmin)
        val btnImprevisto: Button = view.findViewById(R.id.btnImprevistoAdmin)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_solicitud_admin, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listaSolicitudes[position]
        holder.tvIdSolicitud.text = item.idSolicitud
        holder.tvAprendizInfo.text = "Aprendiz: ${item.nombreAprendiz} (${item.correoAprendiz})"
        holder.tvEquipoSolicitado.text = "Equipo: ${item.equipoOEspacio}"
        holder.tvEstado.text = item.estado

        when (item.estado.lowercase()) {
            "pendiente" -> {
                holder.btnAprobar.visibility = View.VISIBLE
                holder.btnRechazar.visibility = View.VISIBLE
                holder.btnEntregar.visibility = View.GONE
                holder.btnImprevisto.visibility = View.GONE
                holder.tvTemporizador.visibility = View.GONE
            }
            "aprobado", "aprobada" -> {
                holder.btnAprobar.visibility = View.GONE
                holder.btnRechazar.visibility = View.GONE
                holder.btnEntregar.visibility = View.VISIBLE
                holder.btnImprevisto.visibility = View.VISIBLE
                holder.tvTemporizador.visibility = View.GONE
            }
            "en préstamo", "en prestamo" -> {
                holder.btnAprobar.visibility = View.GONE
                holder.btnRechazar.visibility = View.GONE
                holder.btnEntregar.visibility = View.GONE
                holder.btnImprevisto.visibility = View.GONE
                holder.tvTemporizador.visibility = View.VISIBLE
                iniciarTemporizadorVisual(holder, item)
            }
        }

        // HU-08: Aprobar
        holder.btnAprobar.setOnClickListener {
            onAprobar(item)
            item.estado = "Aprobado"
            notifyItemChanged(position)
        }

        // HU-08: Rechazar
        holder.btnRechazar.setOnClickListener {
            mostrarModalRechazo(holder.itemView.context, item, position)
        }

        // CA-09.1: Entregar e Iniciar Temporizador
        holder.btnEntregar.setOnClickListener {
            item.estado = "En Préstamo"
            item.tiempoInicioMillis = System.currentTimeMillis()
            onEntregar(item)
            notifyItemChanged(position)
            Toast.makeText(holder.itemView.context, "Equipo entregado. Temporizador iniciado.", Toast.LENGTH_SHORT).show()
        }

        // CA-09.2: Reportar Imprevisto y Reasignar
        holder.btnImprevisto.setOnClickListener {
            mostrarModalReasignacion(holder.itemView.context, item, position)
        }
    }

    // CA-09.1: Lógica del temporizador
    private fun iniciarTemporizadorVisual(holder: ViewHolder, item: SolicitudPendienteAdmin) {
        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                val transcurrido = System.currentTimeMillis() - item.tiempoInicioMillis
                val segundos = (transcurrido / 1000) % 60
                val minutos = (transcurrido / (1000 * 60)) % 60
                val horas = (transcurrido / (1000 * 60 * 60))

                holder.tvTemporizador.text = String.format(
                    Locale.getDefault(),
                    "Tiempo transcurrido: %02d:%02d:%02d",
                    horas, minutos, segundos
                )

                if (item.estado.equals("En Préstamo", ignoreCase = true)) {
                    handler.postDelayed(this, 1000)
                }
            }
        }
        handler.post(runnable)
    }

    // CA-09.2: Modal para reasignación de ítem equivalente
    private fun mostrarModalReasignacion(context: Context, item: SolicitudPendienteAdmin, position: Int) {
        val itemsEquivalentes = arrayOf(
            "${item.equipoOEspacio} (Unidad Reemplazo 02)",
            "${item.equipoOEspacio} (Unidad Reemplazo 03)",
            "Modelo Alternativo Compatible - Serie B"
        )

        var seleccion = 0

        AlertDialog.Builder(context)
            .setTitle("Reportar Imprevisto / Reasignar")
            .setMessage("El equipo asignado presenta fallas. Seleccione un ítem equivalente disponible:")
            .setSingleChoiceItems(itemsEquivalentes, 0) { _, which ->
                seleccion = which
            }
            .setPositiveButton("Reasignar") { _, _ ->
                val nuevoEquipo = itemsEquivalentes[seleccion]
                item.equipoOEspacio = nuevoEquipo
                onReasignar(item, nuevoEquipo)
                notifyItemChanged(position)
                Toast.makeText(context, "Equipo reasignado exitosamente: $nuevoEquipo", Toast.LENGTH_LONG).show()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    // HU-08 Modal Rechazo
    private fun mostrarModalRechazo(context: Context, item: SolicitudPendienteAdmin, position: Int) {
        val inputMotivo = EditText(context).apply {
            hint = "Escribe el motivo del rechazo (Obligatorio)"
            setPadding(32, 32, 32, 32)
        }

        val dialog = AlertDialog.Builder(context)
            .setTitle("Rechazar Solicitud ${item.idSolicitud}")
            .setMessage("Por favor especifique la razón del rechazo:")
            .setView(inputMotivo)
            .setPositiveButton("Confirmar Rechazo", null)
            .setNegativeButton("Cancelar", null)
            .create()

        dialog.setOnShowListener {
            val btnConfirmar = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            btnConfirmar.setOnClickListener {
                val motivo = inputMotivo.text.toString().trim()
                if (motivo.isEmpty()) {
                    inputMotivo.error = "El motivo de rechazo es obligatorio"
                } else {
                    onRechazar(item, motivo)
                    listaSolicitudes.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, listaSolicitudes.size)
                    dialog.dismiss()
                }
            }
        }
        dialog.show()
    }

    override fun getItemCount(): Int = listaSolicitudes.size
}