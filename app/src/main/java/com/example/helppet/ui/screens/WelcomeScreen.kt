package com.example.helppet.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.helppet.MainScreen
import com.example.helppet.ui.screens.auth.LoginScreen
import com.example.helppet.ui.screens.auth.RegisterScreen

@Composable
fun WelcomeRootScreen() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Welcome) }

    when (currentScreen) {
        Screen.Welcome -> WelcomeScreen(
            onLoginClick = { currentScreen = Screen.Login },
            onRegisterClick = { currentScreen = Screen.Register }
        )
        Screen.Login -> LoginScreen(
            onLoginSuccess = { currentScreen = Screen.Main }
        )
        Screen.Register -> RegisterScreen(
            onRegisterSuccess = { currentScreen = Screen.Main }
        )
        Screen.Main -> MainScreen(   onLogout = { currentScreen = Screen.Welcome })
    }
}

enum class Screen {
    Welcome, Login, Register, Main
}

@Composable
fun WelcomeScreen(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().padding(32.dp)
        ) {
            Text("Bem-vindo ao HelpPet!", style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = onLoginClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Entrar")
            }
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = onRegisterClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar")
            }
        }
    }
}
