package com.example.helppet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.helppet.ui.theme.HelpPetTheme
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem

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

@Composable
fun HomeScreen() {
    var nome by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("Cachorro") }
    var descricao by remember { mutableStateOf("") }
    var contato by remember { mutableStateOf("") }
    var endereco by remember { mutableStateOf("") }
    val tipos = listOf("Cachorro", "Gato", "Outro")
    var tipoExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Cadastro de Ocorrência",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome do animal (opcional)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Dropdown Tipo
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = tipo,
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
                label = { Text("Tipo") },
                trailingIcon = {
                    IconButton(onClick = { tipoExpanded = !tipoExpanded }) {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                    }
                }
            )
            DropdownMenu(
                expanded = tipoExpanded,
                onDismissRequest = { tipoExpanded = false }
            ) {
                tipos.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item) },
                        onClick = {
                            tipo = item
                            tipoExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = endereco,
            onValueChange = { endereco = it },
            label = { Text("Endereço ou localização aproximada") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = descricao,
            onValueChange = { descricao = it },
            label = { Text("Descrição do ocorrido") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 5
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = contato,
            onValueChange = { contato = it },
            label = { Text("Contato (telefone ou e-mail)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                println("Ocorrência: $nome, $tipo, $endereco, $descricao, $contato")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar")
        }
    }
}


@Composable
fun NewReportScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Nova Ocorrência")
    }
}



@Composable
fun ListReportsScreen(){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("OCORRENIAS ATUTIAIITIAIIS")
    }
}


@Composable
fun ProfileScreen(){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("PROFILE SCRNEENENNE")
    }
}






sealed class BottomNavItem(val label: String, val icon: ImageVector) {
    object Home : BottomNavItem("Home", Icons.Filled.Home)
    object NewReport : BottomNavItem("Nova", Icons.Filled.AddCircle)
    object ListReports : BottomNavItem("Ocorrências", Icons.Filled.Search)
    object Profile : BottomNavItem("Perfil", Icons.Filled.Person)
}


@Composable
fun BottomNavigationBar(
    selectedItem: BottomNavItem,
    onItemSelected: (BottomNavItem) -> Unit
) {
    NavigationBar {
        listOf(
            BottomNavItem.Home,
            BottomNavItem.NewReport,
            BottomNavItem.ListReports,
            BottomNavItem.Profile
        ).forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = item == selectedItem,
                onClick = { onItemSelected(item) }
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun Preview() {
    HelpPetTheme {
        MainScreen()
    }
}