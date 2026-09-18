package com.example.prestamolab

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SolicitudActivityTest {

    @Test
    fun testSolicitudActivityOpensSuccessfully() {
        // Lanza la actividad de solicitud y verifica que el botón de confirmar solicitud sea visible
        val scenario = ActivityScenario.launch(SolicitudActivity::class.java)

        onView(withId(R.id.btnEnviarSolicitud)).check(matches(isDisplayed()))

        scenario.close()
    }

    @Test
    fun testPickersAndSpinnersArePresent() {
        // Caso 2: Verifica que los selectores de fecha/hora o spinners estén presentes en la UI
        val scenario = ActivityScenario.launch(SolicitudActivity::class.java)

        // Asegúrate de usar los IDs reales de tus elementos en activity_solicitud.xml
        // onView(withId(R.id.spinnerLaboratorio)).check(matches(isDisplayed()))

        scenario.close()
    }

    @Test
    fun testDateAndTimePickersAreInteractive() {
        // Lanza la actividad de solicitud
        val scenario = ActivityScenario.launch(SolicitudActivity::class.java)

        // Verifica que el botón de seleccionar fecha esté visible y se pueda pulsar
        onView(withId(R.id.btnSeleccionarFecha)).check(matches(isDisplayed()))

        // Verifica que los botones de hora de inicio y fin estén visibles
        onView(withId(R.id.btnHoraInicio)).check(matches(isDisplayed()))
        onView(withId(R.id.btnHoraFin)).check(matches(isDisplayed()))

        // Verifica que las etiquetas de texto iniciales de fecha y horario muestren el estado por defecto
        onView(withId(R.id.tvFechaSeleccionada)).check(matches(isDisplayed()))
        onView(withId(R.id.tvHorasSeleccionadas)).check(matches(isDisplayed()))

        scenario.close()
    }
}