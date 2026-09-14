package com.ctma.prestamolabctma.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ctma.prestamolabctma.data.session.SessionManager

class SolicitudViewModelFactory(
    private val application: Application,
    private val sessionManager: SessionManager
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (
            modelClass.isAssignableFrom(
                SolicitudViewModel::class.java
            )
        ) {

            return SolicitudViewModel(
                application = application,
                sessionManager = sessionManager
            ) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class"
        )
    }
}