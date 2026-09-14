package com.example.prestamolab.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.prestamolab.R

object NotificationHelper {

    private const val CHANNEL_ID = "prestamolab_alertas"
    private const val CHANNEL_NAME = "Alertas de Préstamos"

    private fun crearCanal(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notificaciones sobre estado de solicitudes y recordatorios de devolución"
            }
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    // CA-07.1: Notificación Push Instantánea al cambiar de estado
    fun enviarNotificacionEstado(context: Context, idSolicitud: String, nuevoEstado: String) {
        crearCanal(context)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Actualización de Solicitud $idSolicitud")
            .setContentText("Tu solicitud ha sido cambiada al estado: $nuevoEstado")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(idSolicitud.hashCode(), builder.build())
    }

    // CA-07.2: Recordatorio de Devolución a los 30 minutos previos
    fun enviarRecordatorioDevolucion(context: Context, horaLimite: String) {
        crearCanal(context)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("¡Recordatorio de Devolución!")
            .setContentText("Recuerda devolver tus equipos antes de $horaLimite")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(9999, builder.build())
    }
}