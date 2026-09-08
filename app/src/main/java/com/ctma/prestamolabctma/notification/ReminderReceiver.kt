package com.ctma.prestamolabctma.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ReminderReceiver : BroadcastReceiver() {

    override fun onReceive(
        context: Context,
        intent: Intent
    ) {

        val equipo = intent.getStringExtra("equipo")
            ?: "equipo"

        val solicitudId = intent.getIntExtra(
            "solicitud_id",
            0
        )

        NotificationHelper.mostrarNotificacion(
            context = context,
            titulo = "Recordatorio de devolución",
            mensaje = "Faltan 30 minutos para devolver $equipo.",
            id = 1000 + solicitudId
        )
    }
}