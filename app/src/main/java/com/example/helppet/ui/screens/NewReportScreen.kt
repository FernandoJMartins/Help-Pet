package com.example.helppet.ui.screens

import FirebaseOccurrenceDataSource
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.helppet.model.Occurrence
import kotlinx.coroutines.launch

import android.util.Log



@Composable
fun NewReportScreen() {
    var nome by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("Cachorro") }
    var descricao by remember { mutableStateOf("") }
    var contato by remember { mutableStateOf("") }
    var endereco by remember { mutableStateOf("") }
    val tipos = listOf("Cachorro", "Gato", "Outro")
    var tipoExpanded by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val dataSource = FirebaseOccurrenceDataSource()

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
                val occ = Occurrence(
                    id = "d23",
                    name = nome,
                    type = tipo,
                    address = endereco,
                    description = descricao,
                    contact = contato,
                    picsUrl = listOf("asc", "asdasd")
                )

                coroutineScope.launch {
                    try {
                        dataSource.saveOccurrence(occ)
                        println("Ocorrência salva com sucesso")
                        // Limpa os campos após salvar
                        nome = ""
                        tipo = "Cachorro"
                        endereco = ""
                        descricao = ""
                        contato = ""
                    } catch (e: Exception) {
                        println("Erro ao salvar ocorrência: ${e.message}")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar")
        }
    }
}
