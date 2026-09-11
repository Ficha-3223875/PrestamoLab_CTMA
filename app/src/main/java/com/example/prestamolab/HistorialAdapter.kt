package com.example.prestamolab
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.prestamolab.R
import com.example.prestamolab.model.PrestamoHistorial
class HistorialAdapter(
    private val listaPrestamos: List<PrestamoHistorial>
) : RecyclerView.Adapter<HistorialAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvItemNombre: TextView = view.findViewById(R.id.tvItemNombre)
        val tvEstadoIndicador: TextView = view.findViewById(R.id.tvEstadoIndicador)
        val tvIdSolicitud: TextView = view.findViewById(R.id.tvIdSolicitud)
        val tvFechaSolicitud: TextView = view.findViewById(R.id.tvFechaSolicitud)
        val tvFechaLimite: TextView = view.findViewById(R.id.tvFechaLimite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_historial, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val prestamo = listaPrestamos[position]
        holder.tvItemNombre.text = prestamo.itemOEspacio

        holder.tvIdSolicitud.text = "Código: ${prestamo.idSolicitud}"
        holder.tvFechaSolicitud.text = "Solicitado el: ${prestamo.fechaSolicitud}"
        holder.tvFechaLimite.text = "Devolución límite: ${prestamo.fechaLimiteDevolucion}"
        holder.tvEstadoIndicador.text = prestamo.estado
        when (prestamo.estado) {
            "Aprobado" -> holder.tvEstadoIndicador.setTextColor(Color.parseColor("#2E7D32"))
            "Pendiente" -> holder.tvEstadoIndicador.setTextColor(Color.parseColor("#EF6C00"))
            "Devuelto" -> holder.tvEstadoIndicador.setTextColor(Color.parseColor("#1565C0"))
            else -> holder.tvEstadoIndicador.setTextColor(Color.GRAY)
        }
    }

    override fun getItemCount(): Int = listaPrestamos.size
}