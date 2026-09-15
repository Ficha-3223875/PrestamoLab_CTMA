package com.example.prestamolab

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.prestamolab.model.CatalogoItem

class CatalogoAdapter(
    private val listaItems: List<CatalogoItem>
) : RecyclerView.Adapter<CatalogoAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombreEquipo: TextView = view.findViewById(R.id.tvNombreEquipo)
        val tvDisponibilidad: TextView = view.findViewById(R.id.tvDisponibilidad)
        val tvMarcaModelo: TextView = view.findViewById(R.id.tvMarcaModelo)
        val tvDescripcionEquipo: TextView = view.findViewById(R.id.tvDescripcionEquipo)
        val tvCategoria: TextView = view.findViewById(R.id.tvCategoria)
        val btnSolicitarEquipo: Button = view.findViewById(R.id.btnSolicitarEquipo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_catalogo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listaItems[position]

        holder.tvNombreEquipo.text = item.nombre
        holder.tvMarcaModelo.text = "${item.marca} - ${item.modelo}"
        holder.tvDescripcionEquipo.text = item.descripcion
        holder.tvCategoria.text = item.categoria

        if (item.disponible) {
            holder.tvDisponibilidad.text = "Disponible"
            holder.tvDisponibilidad.setTextColor(Color.parseColor("#2E7D32"))
            holder.btnSolicitarEquipo.isEnabled = true
            holder.btnSolicitarEquipo.alpha = 1.0f
        } else {
            holder.tvDisponibilidad.text = "Ocupado"
            holder.tvDisponibilidad.setTextColor(Color.parseColor("#C62828"))
            holder.btnSolicitarEquipo.isEnabled = false
            holder.btnSolicitarEquipo.alpha = 0.5f
        }

        // Navegación a SolicitudActivity pasando la info del equipo
        holder.btnSolicitarEquipo.setOnClickListener {
            if (item.disponible) {
                val context = holder.itemView.context
                val intent = Intent(context, SolicitudActivity::class.java).apply {
                    putExtra("EQUIPO_ID", item.id)
                    putExtra("EQUIPO_NOMBRE", item.nombre)
                }
                context.startActivity(intent)
            } else {
                Toast.makeText(holder.itemView.context, "Este equipo no está disponible actualmente", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int = listaItems.size
}