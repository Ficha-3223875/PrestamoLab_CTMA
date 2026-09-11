package com.example.prestamolab.util

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("PrestamoLabSession", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_USER_TOKEN = "user_token"
        private const val KEY_USER_EMAIL = "user_email"
    }

    // --- MÉTODOS LOCALES ---
    fun guardarUsuarioLocal(correo: String, pass: String) {
        prefs.edit().putString("USER_$correo", pass).apply()
    }

    fun validarUsuarioLocal(correo: String, pass: String): Boolean {
        // Credencial fija de prueba
        if (correo == "angel@sena.edu.co" && pass == "123456") return true

        // Credenciales registradas en la app
        val storedPass = prefs.getString("USER_$correo", null)
        return storedPass != null && storedPass == pass
    }

    // --- MÉTODOS EXISTENTES ---
    fun guardarSesion(token: String?, email: String) {
        val editor = prefs.edit()
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.putString(KEY_USER_TOKEN, token ?: "")
        editor.putString(KEY_USER_EMAIL, email)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun getCorreo(): String {
        return prefs.getString(KEY_USER_EMAIL, "") ?: ""
    }

    fun cerrarSesion() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }
}