package com.example.prestamolab

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.prestamolab.model.PrestamoHistorial

class HistorialAdapter(
    private val listaPrestamos: List<PrestamoHistorial>,
    private val onCancelarClick: ((PrestamoHistorial) -> Unit)? = null
) : RecyclerView.Adapter<HistorialAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvIdSolicitud: TextView = view.findViewById(R.id.tvIdSolicitud)
        val tvEstadoSolicitud: TextView = view.findViewById(R.id.tvEstadoSolicitud)
        val tvItemSolicitado: TextView = view.findViewById(R.id.tvItemSolicitado)
        val tvFechasSolicitud: TextView = view.findViewById(R.id.tvFechasSolicitud)
        val btnCancelarSolicitud: Button = view.findViewById(R.id.btnCancelarSolicitud)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_historial, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listaPrestamos[position]
        holder.tvIdSolicitud.text = item.idSolicitud
        holder.tvItemSolicitado.text = item.itemOEspacio
        holder.tvFechasSolicitud.text = "Fechas: ${item.fechaSolicitud} - ${item.fechaDevolucion}"
        holder.tvEstadoSolicitud.text = item.estado

        when (item.estado.lowercase()) {
            "aprobado" -> {
                holder.tvEstadoSolicitud.setTextColor(Color.parseColor("#2E7D32"))
                holder.btnCancelarSolicitud.visibility = View.VISIBLE
            }
            "pendiente" -> {
                holder.tvEstadoSolicitud.setTextColor(Color.parseColor("#F57C00"))
                holder.btnCancelarSolicitud.visibility = View.VISIBLE
            }
            "cancelada", "cancelado" -> {
                holder.tvEstadoSolicitud.setTextColor(Color.parseColor("#C62828"))
                holder.btnCancelarSolicitud.visibility = View.GONE
            }
            else -> {
                holder.tvEstadoSolicitud.setTextColor(Color.GRAY)
                holder.btnCancelarSolicitud.visibility = View.GONE
            }
        }

        holder.btnCancelarSolicitud.setOnClickListener {
            evaluarCancelar(holder.itemView.context, item)
        }
    }

    private fun evaluarCancelar(context: Context, item: PrestamoHistorial) {
        // CA-06.2: Bloqueo para préstamos Aprobados
        if (item.estado.equals("Aprobado", ignoreCase = true)) {
            Toast.makeText(
                context,
                "Para cancelar un préstamo aprobado, debes contactar al encargado del laboratorio",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        // CA-06.1: Confirmación y cancelación para préstamos Pendientes
        if (item.estado.equals("Pendiente", ignoreCase = true)) {
            AlertDialog.Builder(context)
                .setTitle("Confirmar Cancelación")
                .setMessage("¿Estás seguro de que deseas cancelar la solicitud ${item.idSolicitud}?")
                .setPositiveButton("Sí, Cancelar") { _, _ ->
                    item.estado = "Cancelada"
                    notifyDataSetChanged()
                    onCancelarClick?.invoke(item)
                    Toast.makeText(context, "Solicitud cancelada con éxito", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("No", null)
                .show()
        }
    }

    override fun getItemCount(): Int = listaPrestamos.size
}