package com.ctma.prestamolabctma.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

class ConnectivityObserver(
    context: Context
) {

    private val connectivityManager =
        context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

    fun estaConectado(): Boolean {

        val network =
            connectivityManager.activeNetwork

        val capabilities =
            connectivityManager
                .getNetworkCapabilities(network)

        return capabilities?.hasCapability(
            NetworkCapabilities.NET_CAPABILITY_INTERNET
        ) == true
    }

    fun observarConexion(): Flow<Boolean> =
        callbackFlow {

            val callback =
                object : ConnectivityManager.NetworkCallback() {

                    override fun onAvailable(
                        network: Network
                    ) {
                        trySend(true)
                    }

                    override fun onLost(
                        network: Network
                    ) {
                        trySend(false)
                    }
                }

            trySend(estaConectado())

            connectivityManager
                .registerDefaultNetworkCallback(
                    callback
                )

            awaitClose {

                connectivityManager
                    .unregisterNetworkCallback(
                        callback
                    )
            }
        }
            .distinctUntilChanged()
}