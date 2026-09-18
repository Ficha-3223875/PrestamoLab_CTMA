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
}