package com.example.prestamolab

import android.content.Context
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

class AdminSolicitudesAdapter(
    private var listaSolicitudes: MutableList<SolicitudPendienteAdmin>,
    private val onAprobar: (SolicitudPendienteAdmin) -> Unit,
    private val onRechazar: (SolicitudPendienteAdmin, String) -> Unit
) : RecyclerView.Adapter<AdminSolicitudesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvIdSolicitud: TextView = view.findViewById(R.id.tvIdSolicitudAdmin)
        val tvHoraLlegada: TextView = view.findViewById(R.id.tvHoraLlegada)
        val tvAprendizInfo: TextView = view.findViewById(R.id.tvAprendizInfo)
        val tvEquipoSolicitado: TextView = view.findViewById(R.id.tvEquipoSolicitado)
        val btnAprobar: Button = view.findViewById(R.id.btnAprobarAdmin)
        val btnRechazar: Button = view.findViewById(R.id.btnRechazarAdmin)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_solicitud_admin, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listaSolicitudes[position]
        holder.tvIdSolicitud.text = item.idSolicitud
        holder.tvHoraLlegada.text = item.fechaHoraLlegada
        holder.tvAprendizInfo.text = "Aprendiz: ${item.nombreAprendiz} (${item.correoAprendiz})"
        holder.tvEquipoSolicitado.text = "Equipo: ${item.equipoOEspacio}"

        holder.btnAprobar.setOnClickListener {
            onAprobar(item)
            removerItem(position)
            Toast.makeText(holder.itemView.context, "Solicitud aprobada", Toast.LENGTH_SHORT).show()
        }

        holder.btnRechazar.setOnClickListener {
            mostrarModalRechazo(holder.itemView.context, item, position)
        }
    }

    // CA-08.2: Modal con validación estricta de motivo obligatorio
    private fun mostrarModalRechazo(context: Context, item: SolicitudPendienteAdmin, position: Int) {
        val inputMotivo = EditText(context).apply {
            hint = "Escribe el motivo del rechazo (Obligatorio)"
            setPadding(32, 32, 32, 32)
        }

        val dialog = AlertDialog.Builder(context)
            .setTitle("Rechazar Solicitud ${item.idSolicitud}")
            .setMessage("Por favor especifique la razón del rechazo:")
            .setView(inputMotivo)
            .setPositiveButton("Confirmar Rechazo", null) // Se maneja null para evitar cierre automático en fallo
            .setNegativeButton("Cancelar", null)
            .create()

        dialog.setOnShowListener {
            val btnConfirmar = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            btnConfirmar.setOnClickListener {
                val motivo = inputMotivo.text.toString().trim()
                if (motivo.isEmpty()) {
                    inputMotivo.error = "El motivo de rechazo es obligatorio"
                    Toast.makeText(context, "Debe ingresar un motivo para rechazar la solicitud", Toast.LENGTH_SHORT).show()
                } else {
                    onRechazar(item, motivo)
                    removerItem(position)
                    dialog.dismiss()
                    Toast.makeText(context, "Solicitud rechazada correctamente", Toast.LENGTH_SHORT).show()
                }
            }
        }

        dialog.show()
    }

    private fun removerItem(position: Int) {
        if (position in 0 until listaSolicitudes.size) {
            listaSolicitudes.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, listaSolicitudes.size)
        }
    }

    override fun getItemCount(): Int = listaSolicitudes.size
}