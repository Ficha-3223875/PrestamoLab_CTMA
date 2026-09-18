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
class ReportesActivityTest {

    @Test
    fun testReportesActivityOpensSuccessfully() {
        // Lanza la Activity de Reportes
        val scenario = ActivityScenario.launch(ReportesActivity::class.java)

        // Verifica que el botón de generar reporte esté visible en la pantalla
        onView(withId(R.id.btnGenerarReporte)).check(matches(isDisplayed()))

        scenario.close()
    }
}