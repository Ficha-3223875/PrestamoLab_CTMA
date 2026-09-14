package com.ctma.prestamolabctma.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ctma.prestamolabctma.data.session.SessionManager

class SolicitudViewModelFactory(
    private val sessionManager: SessionManager
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (modelClass.isAssignableFrom(SolicitudViewModel::class.java)) {

            @Suppress("UNCHECKED_CAST")
            return SolicitudViewModel(
                sessionManager
            ) as T
        }

        throw IllegalArgumentException(
            "ViewModel desconocido"
        )
    }
}