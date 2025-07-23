package com.example.helppet

import HomeScreen
import ListReportsScreen

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
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.example.helppet.ui.theme.HelpPetTheme
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import com.example.helppet.ui.components.BottomNavigationBar
import com.example.helppet.ui.screens.NewReportScreen
import com.example.helppet.ui.screens.ProfileScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HelpPetTheme {
            MainScreen()
        }
    }
}}



@Composable
fun MainScreen(){
    var selectedItem by remember { mutableStateOf<BottomNavItem>(BottomNavItem.Home) }

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
                BottomNavItem.Home -> HomeScreen()
                BottomNavItem.NewReport -> NewReportScreen()
                BottomNavItem.ListReports -> ListReportsScreen()
                BottomNavItem.Profile -> ProfileScreen()
            }
        }
    }
}


sealed class BottomNavItem(val label: String, val icon: ImageVector) {
    object Home : BottomNavItem("Home", Icons.Filled.Home)
    object NewReport : BottomNavItem("Nova", Icons.Filled.AddCircle)
    object ListReports : BottomNavItem("Ocorrências", Icons.Filled.Search)
    object Profile : BottomNavItem("Perfil", Icons.Filled.Person)
}




@Preview(showBackground = true)
@Composable
fun Preview() {
    HelpPetTheme {
        MainScreen()
    }
}