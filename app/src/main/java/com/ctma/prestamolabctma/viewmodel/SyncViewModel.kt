package com.ctma.prestamolabctma.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ctma.prestamolabctma.data.local.AppDatabase
import com.ctma.prestamolabctma.data.network.ConnectivityObserver
import com.ctma.prestamolabctma.data.repository.PendingActionRepository
import com.ctma.prestamolabctma.data.sync.PendingActionSyncManager

class SyncViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val connectivityObserver =
        ConnectivityObserver(application)

    private val pendingActionRepository =
        PendingActionRepository(
            AppDatabase
                .getDatabase(application)
                .pendingActionDao()
        )

    private val syncManager =
        PendingActionSyncManager(
            pendingActionRepository
        )

    init {

        syncManager.iniciarSincronizacion(
            scope = viewModelScope,
            conexion =
                connectivityObserver
                    .observarConexion()
        )
    }

    override fun onCleared() {

        syncManager.detenerSincronizacion()

        super.onCleared()
    }
}