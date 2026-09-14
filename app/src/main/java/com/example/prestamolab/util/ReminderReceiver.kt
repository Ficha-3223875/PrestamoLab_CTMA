package com.example.prestamolab.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val horaLimite = intent.getStringExtra("HORA_LIMITE") ?: "la hora pactada"
        NotificationHelper.enviarRecordatorioDevolucion(context, horaLimite)
    }
}