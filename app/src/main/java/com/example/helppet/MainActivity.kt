package com.example.helppet

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.example.helppet.model.User
import com.example.helppet.ui.theme.HelpPetTheme
import com.example.helppet.ui.components.BottomNavigationBar
import com.example.helppet.ui.screens.HomeScreen
import com.example.helppet.ui.screens.NewReportScreen
import com.example.helppet.ui.screens.ProfileScreen
import com.example.helppet.ui.screens.WelcomeRootScreen
import com.example.helppet.viewmodels.OccurrenceViewModel
import com.example.helppet.viewmodels.UserViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HelpPetTheme {
                WelcomeRootScreen()
        }
    }
}}


@Composable
fun MainScreen(
    onLogout: () -> Unit,
    userViewModel: UserViewModel,
    occurrenceViewModel: OccurrenceViewModel
) {
    var selectedItem by remember { mutableStateOf<BottomNavItem>(BottomNavItem.Home) }
    val userState = remember { mutableStateOf(User.currentUser) }


    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedItem = selectedItem,
                onItemSelected = { selectedItem = it }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedItem) {
                BottomNavItem.NewReport -> NewReportScreen(userState = userState)
                BottomNavItem.Home -> HomeScreen(occurrenceViewModel = occurrenceViewModel)
                BottomNavItem.Profile -> ProfileScreen(onLogout = onLogout, userState = userState)
            }
        }
    }
}


sealed class BottomNavItem(val label: String, val icon: ImageVector) {
    object NewReport : BottomNavItem("Nova", Icons.Filled.AddCircle)
    object Home : BottomNavItem("Home", Icons.Filled.Home)
    object Profile : BottomNavItem("Perfil", Icons.Filled.Person)
}



@Preview(showBackground = true)
@Composable
fun Preview() {
    HelpPetTheme {
        WelcomeRootScreen()
    }
}