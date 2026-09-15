package com.example.prestamolab

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.prestamolab.model.SolicitudPendienteAdmin

class AdminSolicitudesAdapter(
    private val listaSolicitudes: MutableList<SolicitudPendienteAdmin>,
    private val onAprobar: (SolicitudPendienteAdmin) -> Unit,
    private val onRechazar: (SolicitudPendienteAdmin) -> Unit,
    private val onEntregar: (SolicitudPendienteAdmin) -> Unit,
    private val onImprevisto: (SolicitudPendienteAdmin) -> Unit,
    private val onDevolver: (SolicitudPendienteAdmin, position: Int) -> Unit
) : RecyclerView.Adapter<AdminSolicitudesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvIdSolicitudAdmin: TextView = view.findViewById(R.id.tvIdSolicitudAdmin)
        val tvEstadoAdmin: TextView = view.findViewById(R.id.tvEstadoAdmin)
        val tvAprendizInfo: TextView = view.findViewById(R.id.tvAprendizInfo)
        val tvEquipoSolicitado: TextView = view.findViewById(R.id.tvEquipoSolicitado)
        val tvTemporizador: TextView = view.findViewById(R.id.tvTemporizador)

        val btnAprobarAdmin: Button = view.findViewById(R.id.btnAprobarAdmin)
        val btnRechazarAdmin: Button = view.findViewById(R.id.btnRechazarAdmin)
        val btnEntregarAdmin: Button = view.findViewById(R.id.btnEntregarAdmin)
        val btnImprevistoAdmin: Button = view.findViewById(R.id.btnImprevistoAdmin)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_solicitud_admin, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listaSolicitudes[position]

        holder.tvIdSolicitudAdmin.text = item.idSolicitud
        holder.tvEstadoAdmin.text = item.estado
        holder.tvAprendizInfo.text = "Aprendiz: ${item.nombreAprendiz} (${item.correoAprendiz})"
        holder.tvEquipoSolicitado.text = "Equipo: ${item.equipoOEspacio}"

        // Gestión visual por estados (HU-08, HU-09, HU-10)
        when (item.estado) {
            "Pendiente" -> {
                holder.tvEstadoAdmin.setTextColor(Color.parseColor("#F57C00"))
                holder.btnAprobarAdmin.visibility = View.VISIBLE
                holder.btnRechazarAdmin.visibility = View.VISIBLE
                holder.btnEntregarAdmin.visibility = View.GONE
                holder.btnImprevistoAdmin.visibility = View.GONE
                holder.tvTemporizador.visibility = View.GONE
            }
            "En Préstamo", "Entregado" -> {
                holder.tvEstadoAdmin.setTextColor(Color.parseColor("#1976D2"))
                holder.btnAprobarAdmin.visibility = View.GONE
                holder.btnRechazarAdmin.visibility = View.GONE
                holder.btnEntregarAdmin.visibility = View.VISIBLE
                holder.btnEntregarAdmin.text = "Recibir Devolución"
                holder.btnImprevistoAdmin.visibility = View.VISIBLE
                holder.tvTemporizador.visibility = View.VISIBLE
            }
            "Devuelto" -> {
                holder.tvEstadoAdmin.setTextColor(Color.parseColor("#2E7D32"))
                holder.btnAprobarAdmin.visibility = View.GONE
                holder.btnRechazarAdmin.visibility = View.GONE
                holder.btnEntregarAdmin.visibility = View.VISIBLE
                holder.btnEntregarAdmin.text = "Devuelto"
                holder.btnEntregarAdmin.isEnabled = false
                holder.btnImprevistoAdmin.visibility = View.GONE
                holder.tvTemporizador.visibility = View.GONE
            }
        }

        holder.btnAprobarAdmin.setOnClickListener { onAprobar(item) }
        holder.btnRechazarAdmin.setOnClickListener { onRechazar(item) }
        holder.btnImprevistoAdmin.setOnClickListener { onImprevisto(item) }

        holder.btnEntregarAdmin.setOnClickListener {
            if (item.estado == "En Préstamo" || item.estado == "Entregado") {
                // Mitigación de peticiones duplicadas por múltiples clics seguidos
                holder.btnEntregarAdmin.isEnabled = false
                onDevolver(item, position)
            } else {
                onEntregar(item)
            }
        }
    }

    override fun getItemCount(): Int = listaSolicitudes.size
}