package com.example.prestamolab

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.prestamolab.model.CatalogoItem

class CatalogoAdapter(
    private val listaCatalogo: List<CatalogoItem>
) : RecyclerView.Adapter<CatalogoAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombreItem: TextView = view.findViewById(R.id.tvNombreItem)
        val tvMarcaModelo: TextView = view.findViewById(R.id.tvMarcaModelo)
        val tvCaracteristicas: TextView = view.findViewById(R.id.tvCaracteristicas)
        val tvCategoriaItem: TextView = view.findViewById(R.id.tvCategoriaItem)
        val tvEstadoDisponibilidad: TextView = view.findViewById(R.id.tvEstadoDisponibilidad)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_catalogo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listaCatalogo[position]
        holder.tvNombreItem.text = item.nombre
        holder.tvMarcaModelo.text = "${item.marca} - ${item.modelo}"
        holder.tvCaracteristicas.text = item.caracteristicas
        holder.tvCategoriaItem.text = item.categoria

        if (item.disponible) {
            holder.tvEstadoDisponibilidad.text = "Disponible"
            holder.tvEstadoDisponibilidad.setTextColor(Color.parseColor("#2E7D32"))
        } else {
            holder.tvEstadoDisponibilidad.text = "Ocupado"
            holder.tvEstadoDisponibilidad.setTextColor(Color.parseColor("#C62828"))
        }
    }

    override fun getItemCount(): Int = listaCatalogo.size
}