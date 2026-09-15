package com.example.prestamolab

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prestamolab.api.RetrofitClient
import com.example.prestamolab.model.CatalogoItem
import com.example.prestamolab.model.PrestamoHistorial
import com.example.prestamolab.util.NetworkHelper
import com.example.prestamolab.util.NotificationHelper
import com.example.prestamolab.util.ReminderReceiver
import com.example.prestamolab.util.SessionManager
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            setContentView(R.layout.activity_main)
            sessionManager = SessionManager(this)

            // Permisos protegidos para evitar cierres inmediatos en emuladores recientes
            solicitarPermisoNotificaciones()

            val tvBienvenida = findViewById<TextView?>(R.id.tvBienvenida)
            val rvCatalogo = findViewById<RecyclerView?>(R.id.rvCatalogo)
            val rvHistorial = findViewById<RecyclerView?>(R.id.rvHistorial)
            val btnCerrarSesion = findViewById<Button?>(R.id.btnCerrarSesion)

            val correoUsuario = sessionManager.getCorreo()
            tvBienvenida?.text = if (!correoUsuario.isNullOrEmpty()) "Hola, $correoUsuario" else "Bienvenido a PrestamoLab"

            rvCatalogo?.layoutManager = LinearLayoutManager(this)
            rvHistorial?.layoutManager = LinearLayoutManager(this)

            if (rvCatalogo != null) {
                cargarCatalogo(rvCatalogo)
            }

            if (rvHistorial != null) {
                cargarHistorial(rvHistorial)
            }

            btnCerrarSesion?.setOnClickListener {
                sessionManager.cerrarSesion()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

        } catch (e: Exception) {
            Log.e("MainActivityCrash", "Error capturado en onCreate: ${e.message}", e)
            Toast.makeText(this, "Error al cargar la interfaz principal: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
        }
    }

    private fun solicitarPermisoNotificaciones() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 101)
                }
            }
        } catch (e: Exception) {
            Log.e("MainActivityCrash", "Error solicitando permisos: ${e.message}")
        }
    }

    fun simularNotificacionAdmin(idSolicitud: String, nuevoEstado: String) {
        try {
            NotificationHelper.enviarNotificacionEstado(this, idSolicitud, nuevoEstado)
        } catch (e: Exception) {
            Log.e("MainActivityCrash", "Error en notificación: ${e.message}")
        }
    }

    fun programarRecordatorio30Min(horaLimite: String, milisegundosParaLimite: Long) {
        try {
            val intent = Intent(this, ReminderReceiver::class.java).apply {
                putExtra("HORA_LIMITE", horaLimite)
            }
            val pendingIntent = PendingIntent.getBroadcast(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val tiempoAlarma = System.currentTimeMillis() + milisegundosParaLimite
            alarmManager.set(AlarmManager.RTC_WAKEUP, tiempoAlarma, pendingIntent)
            Toast.makeText(this, "Recordatorio programado para $horaLimite", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.e("MainActivityCrash", "Error programando alarma: ${e.message}")
        }
    }

    private fun cargarCatalogo(rv: RecyclerView) {
        val equiposLaboratorio = listOf(
            CatalogoItem("1", "Microscopio Binocular", "Laboratorio Biología", "Zeiss", "Primo Star", "Aumento 1000x, iluminación LED, platina mecánica de precisión.", true),
            CatalogoItem("2", "Osciloscopio Digital", "Electrónica", "Tektronix", "TBS1052B", "2 Canales, 50 MHz de ancho de banda, pantalla a color 7 pulgadas.", true),
            CatalogoItem("3", "Kit Robótica Arduino", "Mecatrónica", "Arduino", "Uno R3", "Incluye placa Uno R3, servomotores, pantalla LCD, sensores y cableado.", false),
            CatalogoItem("4", "Multímetro Digital Pro", "Medición", "Fluke", "117 Electrician's", "Medición True-RMS, detección de voltaje sin contacto VolTect.", true)
        )

        rv.adapter = CatalogoAdapter(equiposLaboratorio)

        lifecycleScope.launch {
            try {
                NetworkHelper.ejecutarPeticionSegura(
                    context = this@MainActivity,
                    call = { RetrofitClient.instance.getCatalogo() },
                    onExito = { respuesta ->
                        if (!respuesta.isNullOrEmpty()) {
                            rv.adapter = CatalogoAdapter(respuesta)
                        }
                    }
                )
            } catch (e: Exception) {
                Log.e("MainActivityCrash", "Error cargando catálogo desde red: ${e.message}")
            }
        }
    }

    private fun cargarHistorial(rv: RecyclerView) {
        val listaHistorial = listOf(
            PrestamoHistorial("SOL-101", "Microscopio Binocular", "2026-03-01", "2026-03-05", "Aprobado"),
            PrestamoHistorial("SOL-102", "Osciloscopio Digital", "2026-03-10", "2026-03-12", "Pendiente")
        )

        rv.adapter = HistorialAdapter(listaHistorial)

        val correo = sessionManager.getCorreo() ?: "aprendiz@sena.edu.co"

        lifecycleScope.launch {
            try {
                NetworkHelper.ejecutarPeticionSegura(
                    context = this@MainActivity,
                    call = { RetrofitClient.instance.getHistorial(correo) },
                    onExito = { respuesta ->
                        if (!respuesta.isNullOrEmpty()) {
                            rv.adapter = HistorialAdapter(respuesta)
                        }
                    }
                )
            } catch (e: Exception) {
                Log.e("MainActivityCrash", "Error cargando historial desde red: ${e.message}")
            }
        }
    }
}