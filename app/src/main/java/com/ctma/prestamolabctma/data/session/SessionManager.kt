package com.ctma.prestamolabctma.data.session

import android.content.Context

class SessionManager(context: Context) {

    private val preferencias =
        context.getSharedPreferences(
            "prestamolab_session",
            Context.MODE_PRIVATE
        )

    fun guardarSesion(
        correo: String,
        rol: String
    ) {

        preferencias.edit()
            .putBoolean("sesion_activa", true)
            .putString("correo", correo)
            .putString("rol", rol)
            .apply()
    }

    fun haySesionActiva(): Boolean {

        return preferencias.getBoolean(
            "sesion_activa",
            false
        )
    }

    fun obtenerCorreo(): String {

        return preferencias.getString(
            "correo",
            ""
        ) ?: ""
    }

    fun obtenerRol(): String {

        return preferencias.getString(
            "rol",
            ""
        ) ?: ""
    }

    fun guardarSancion(
        fechaDesbloqueo: Long
    ) {

        preferencias.edit()
            .putLong(
                "fecha_desbloqueo",
                fechaDesbloqueo
            )
            .apply()
    }

    fun estaSancionado(): Boolean {

        val fechaDesbloqueo =
            preferencias.getLong(
                "fecha_desbloqueo",
                0L
            )

        if (fechaDesbloqueo == 0L) {
            return false
        }

        if (System.currentTimeMillis() >= fechaDesbloqueo) {

            preferencias.edit()
                .remove("fecha_desbloqueo")
                .apply()

            return false
        }

        return true
    }

    fun obtenerFechaDesbloqueo(): Long {

        return preferencias.getLong(
            "fecha_desbloqueo",
            0L
        )
    }

    fun cerrarSesion() {

        preferencias.edit()
            .clear()
            .apply()
    }
}