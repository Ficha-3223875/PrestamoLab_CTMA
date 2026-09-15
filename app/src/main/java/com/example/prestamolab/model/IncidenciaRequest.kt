package com.example.prestamolab.model

import com.google.gson.annotations.SerializedName

data class IncidenciaRequest(
    @SerializedName("idSolicitud") val idSolicitud: String,
    @SerializedName("observaciones") val observaciones: String,
    @SerializedName("estado") val estado: String = "En Mantenimiento"
)

data class IncidenciaResponse(
    @SerializedName("exito") val exito: Boolean,
    @SerializedName("mensaje") val mensaje: String
)