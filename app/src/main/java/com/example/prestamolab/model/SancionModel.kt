package com.example.prestamolab.model

import com.google.gson.annotations.SerializedName

data class SancionResponse(
    @SerializedName("exito") val exito: Boolean,
    @SerializedName("mensaje") val mensaje: String,
    @SerializedName("sancionado") val sancionado: Boolean,
    @SerializedName("diasSancion") val diasSancion: Int? = 3,
    @SerializedName("fechaFinSancion") val fechaFinSancion: String? = null
)

data class EstadoUsuarioResponse(
    @SerializedName("sancionado") val sancionado: Boolean,
    @SerializedName("fechaFinSancion") val fechaFinSancion: String? = null,
    @SerializedName("motivo") val motivo: String? = null
)