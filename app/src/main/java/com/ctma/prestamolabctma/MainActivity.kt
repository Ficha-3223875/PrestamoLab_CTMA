package com.ctma.prestamolabctma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ctma.prestamolabctma.data.local.AppDatabase
import com.ctma.prestamolabctma.data.repository.LoginRepository
import com.ctma.prestamolabctma.data.session.SessionManager
import com.ctma.prestamolabctma.navigation.AppNavigation
import com.ctma.prestamolabctma.notification.NotificationHelper
import com.ctma.prestamolabctma.ui.theme.PrestamoLabCTMATheme
import com.ctma.prestamolabctma.viewmodel.LoginViewModel
import com.ctma.prestamolabctma.viewmodel.LoginViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val sessionManager =
            SessionManager(applicationContext)

        NotificationHelper.crearCanal(this)

        setContent {

            PrestamoLabCTMATheme {

                val database =
                    AppDatabase.getDatabase(
                        applicationContext
                    )

                val loginViewModel: LoginViewModel =
                    viewModel(
                        factory = LoginViewModelFactory(
                            LoginRepository(
                                database.usuarioDao()
                            ),
                            sessionManager
                        )
                    )

                AppNavigation(
                    loginViewModel = loginViewModel,
                    sesionActiva =
                        sessionManager.haySesionActiva()
                )
            }
        }
    }
}