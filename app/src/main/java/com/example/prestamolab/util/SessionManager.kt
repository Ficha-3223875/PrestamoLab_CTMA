package com.example.prestamolab.util

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("PrestamoLabPrefs", Context.MODE_PRIVATE)

    fun guardarSesion(email: String, token: String? = null) {
        prefs.edit().apply {
            putString("USER_EMAIL", email)
            putString("USER_TOKEN", token)
            putBoolean("IS_LOGGED_IN", true)
            apply()
        }
    }

    // Validación local offline utilizada por LoginActivity
    fun validarUsuarioLocal(correo: String, pass: String): Boolean {
        return correo.isNotEmpty() && pass.isNotEmpty()
    }

    fun isLoggedIn(): Boolean = prefs.getBoolean("IS_LOGGED_IN", false)

    // Sostenemos ambos métodos para compatibilidad con MainActivity y SolicitudActivity
    fun getUserEmail(): String? = prefs.getString("USER_EMAIL", "aprendiz@sena.edu.co")
    fun getCorreo(): String? = getUserEmail()

    // HU-12: Gestión de Sanciones por entregas tardías
    fun setUsuarioSancionado(sancionado: Boolean, fechaFin: String? = null) {
        prefs.edit().apply {
            putBoolean("IS_SANCIONADO", sancionado)
            putString("FECHA_FIN_SANCION", fechaFin)
            apply()
        }
    }

    fun isUsuarioSancionado(): Boolean = prefs.getBoolean("IS_SANCIONADO", false)

    fun getFechaFinSancion(): String? = prefs.getString("FECHA_FIN_SANCION", null)

    fun cerrarSesion() {
        prefs.edit().clear().apply()
    }
}