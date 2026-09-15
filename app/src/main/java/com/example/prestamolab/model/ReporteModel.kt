package com.example.prestamolab.model

import com.google.gson.annotations.SerializedName

data class ReporteResumen(
    @SerializedName("totalPrestamos") val totalPrestamos: Int = 0,
    @SerializedName("devolucionesATiempo") val devolucionesATiempo: Int = 0,
    @SerializedName("incidenciasReportadas") val incidenciasReportadas: Int = 0,
    @SerializedName("ficha") val ficha: String? = null,
    @SerializedName("fechaInicio") val fechaInicio: String? = null,
    @SerializedName("fechaFin") val fechaFin: String? = null
)