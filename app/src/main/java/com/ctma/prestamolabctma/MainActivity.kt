package com.ctma.prestamolabctma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ctma.prestamolabctma.navigation.AppNavigation
import com.ctma.prestamolabctma.ui.theme.PrestamoLabCTMATheme
import com.ctma.prestamolabctma.viewmodel.LoginViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            PrestamoLabCTMATheme {

                val loginViewModel: LoginViewModel = viewModel()

                AppNavigation(
                    loginViewModel = loginViewModel
                )
            }
        }
    }
}