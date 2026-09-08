package com.ctma.prestamolabctma.notification

import android.Manifest
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.ctma.prestamolabctma.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object NotificationHelper {

    private const val CHANNEL_ID = "prestamolab_notificaciones"

    fun crearCanal(context: Context) {

        val canal = NotificationChannel(
            CHANNEL_ID,
            "Notificaciones de PrestamoLab",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Avisos sobre solicitudes y préstamos"
        }

        val manager = context.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager

        manager.createNotificationChannel(canal)
    }

    fun mostrarNotificacion(
        context: Context,
        titulo: String,
        mensaje: String,
        id: Int
    ) {

        if (
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val notificacion = NotificationCompat.Builder(
            context,
            CHANNEL_ID
        )
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(titulo)
            .setContentText(mensaje)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat
            .from(context)
            .notify(id, notificacion)
    }

    fun programarRecordatorio(
        context: Context,
        fechaDevolucion: String,
        equipo: String,
        solicitudId: Int
    ) {

        try {

            val formato = SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.getDefault()
            )

            val fecha = formato.parse(fechaDevolucion)
                ?: return

            val calendario = Calendar.getInstance()

            calendario.time = fecha

            // Hora predeterminada de devolución: 12:00 PM
            calendario.set(
                Calendar.HOUR_OF_DAY,
                12
            )
            calendario.set(
                Calendar.MINUTE,
                0
            )
            calendario.set(
                Calendar.SECOND,
                0
            )
            calendario.set(
                Calendar.MILLISECOND,
                0
            )

            // 30 minutos antes
            calendario.add(
                Calendar.MINUTE,
                -30
            )

            if (calendario.timeInMillis <= System.currentTimeMillis()) {
                return
            }

            val intent = Intent(
                context,
                ReminderReceiver::class.java
            ).apply {

                putExtra(
                    "equipo",
                    equipo
                )

                putExtra(
                    "solicitud_id",
                    solicitudId
                )
            }

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                solicitudId,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or
                        PendingIntent.FLAG_IMMUTABLE
            )

            val alarmManager = context.getSystemService(
                Context.ALARM_SERVICE
            ) as AlarmManager

            alarmManager.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendario.timeInMillis,
                pendingIntent
            )

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}