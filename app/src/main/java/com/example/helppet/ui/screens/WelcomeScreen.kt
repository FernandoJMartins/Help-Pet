package com.example.helppet.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.helppet.MainScreen
import com.example.helppet.R
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
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF2F2F2))
                .padding(horizontal = 32.dp, vertical = 24.dp)
        ) {

            Image(
                painter = painterResource(id = R.drawable.logo2),
                contentDescription = "Logo HelpPet",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(140.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(32.dp))


            Text(
                text = "Bem-vindo ao HelpPet",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
            )

            Spacer(modifier = Modifier.height(16.dp))


            Text(
                text = "Conectando pessoas para reencontrar pets perdidos.\n\n" +
                        "O HelpPet é uma plataforma dedicada a facilitar a localização " +
                        "de cães e gatos desaparecidos, unindo tutores e a comunidade " +
                        "em uma rede de apoio e solidariedade.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.DarkGray,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(48.dp))


            Button(
                onClick = onLoginClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Entrar", style = MaterialTheme.typography.titleMedium)
            }
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = onRegisterClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Registrar", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}
