package com.ctma.prestamolabctma

import androidx.compose.foundation.layout.padding
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ctma.prestamolabctma.ui.login.LoginScreen
import com.ctma.prestamolabctma.ui.theme.PrestamoLabCTMATheme
import com.ctma.prestamolabctma.viewmodel.LoginViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            PrestamoLabCTMATheme {

                val loginViewModel: LoginViewModel = viewModel()

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    LoginScreen(
                        loginViewModel = loginViewModel,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}