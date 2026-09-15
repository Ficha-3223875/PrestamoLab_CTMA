package com.example.prestamolab

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.prestamolab.model.RecursoInventario

class InventarioAdapter(
    private val lista: List<RecursoInventario>,
    private val onEditar: (RecursoInventario, Int) -> Unit,
    private val onEliminar: (RecursoInventario, Int) -> Unit
) : RecyclerView.Adapter<InventarioAdapter.InventarioViewHolder>() {

    class InventarioViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre: TextView = view.findViewById(R.id.tvNombreRecurso)
        val tvCodigoTipo: TextView = view.findViewById(R.id.tvCodigoTipo)
        val tvEstado: TextView = view.findViewById(R.id.tvEstadoDisponibilidad)
        val btnEditar: Button = view.findViewById(R.id.btnEditar)
        val btnEliminar: Button = view.findViewById(R.id.btnEliminar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventarioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_inventario, parent, false)
        return InventarioViewHolder(view)
    }

    override fun onBindViewHolder(holder: InventarioViewHolder, position: Int) {
        val recurso = lista[position]
        holder.tvNombre.text = recurso.nombre
        holder.tvCodigoTipo.text = "Código: ${recurso.codigo} | Tipo: ${recurso.tipo}"
        holder.tvEstado.text = "Estado: ${recurso.estado} | Disponible: ${if (recurso.disponibilidad) "Sí" else "No"}"

        holder.btnEditar.setOnClickListener { onEditar(recurso, position) }
        holder.btnEliminar.setOnClickListener { onEliminar(recurso, position) }
    }

    override fun getItemCount(): Int = lista.size
}