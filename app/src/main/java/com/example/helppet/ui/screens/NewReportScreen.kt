package com.example.helppet.ui.screens

import com.example.helppet.data.repository.FirebaseOccurrenceDao
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
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.PickMultipleVisualMedia
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.google.firebase.Firebase

import com.google.firebase.storage.storage
import kotlinx.coroutines.tasks.await
import androidx.compose.material.icons.filled.CameraAlt

import androidx.compose.ui.draw.clip
import com.example.helppet.data.repository.FirebaseUserDao
import com.example.helppet.model.User

import java.util.UUID

suspend fun handleImgUpload(fotos: List<Uri>): List<String> {
    val storage = Firebase.storage("gs://helppet-18262.firebasestorage.app")
    val uploadedUrls = mutableListOf<String>()

    try {
        for (foto in fotos) {
            val fileName = foto.lastPathSegment ?: "image_${System.currentTimeMillis()}"
            val imagesRef = storage.reference.child("fotos/$fileName")

            imagesRef.putFile(foto).await()
            val url = imagesRef.downloadUrl.await()
            Log.d("Upload", "Uploaded $fileName -> $url")
            uploadedUrls.add(url.toString())
        }
    } catch (e: Exception) {
        Log.e("Upload", "Erro ao enviar imagem: $e")
     }
    return uploadedUrls;
}




@Composable
fun NewReportScreen() {
    var nome by remember { mutableStateOf("") }
    var fotos by remember { mutableStateOf<List<Uri>>(emptyList()) }
    var fotosUrl by remember { mutableStateOf<List<String>>(emptyList()) }
    var tipo by remember { mutableStateOf("Cachorro") }
    var descricao by remember { mutableStateOf("") }
    var contato by remember { mutableStateOf("") }
    var endereco by remember { mutableStateOf("") }
    val tipos = listOf("Cachorro", "Gato", "Outro")
    var tipoExpanded by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    val dataSource = FirebaseOccurrenceDao()
    val dataUserSource = FirebaseUserDao()

    val currentUser = User.currentUser



    val pickMultipleMedia = rememberLauncherForActivityResult(
        contract = PickMultipleVisualMedia()
    ) { uris: List<Uri> ->
        coroutineScope.launch {
            fotos = uris
            fotosUrl = handleImgUpload(uris)
        }
    }

    val updateImgs: suspend () -> Unit = {
        fotosUrl = handleImgUpload(fotos)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2))
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Cadastrar Ocorrência",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome do animal") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Box(modifier = Modifier.fillMaxWidth().clickable { tipoExpanded = !tipoExpanded }) {
            OutlinedTextField(
                value = tipo,
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
                label = { Text("Tipo") },
                trailingIcon = {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Abrir menu")
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
            label = { Text("Descrição do animal") },
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

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                pickMultipleMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(12.dp),
        ) {
            Icon(
                imageVector = Icons.Filled.CameraAlt,
                contentDescription = "Ícone de câmera",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text("Adicionar Imagem", fontWeight = FontWeight.SemiBold)
        }

        // Preview das imagens
        if (fotos.isNotEmpty()) {
            Spacer(modifier = Modifier.height(20.dp))
            Text("Imagens selecionadas:", fontWeight = FontWeight.Bold, fontSize = 16.sp)

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                fotos.forEachIndexed { index, url ->
                    item {
                        Box(modifier = Modifier.height(100.dp)) {
                            AsyncImage(
                                model = url,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(12.dp)),
                                contentScale = ContentScale.Crop
                            )
                            IconButton(
                                onClick = {
                                    fotos = fotos.toMutableList().also { it.removeAt(index) }

                                    coroutineScope.launch {
                                        updateImgs()
                                    }
                                },
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .background(Color.Black.copy(alpha = 0.5f))
                                    .width(35.dp)
                                    .height(35.dp)
                            ) {
                                Text("✕", color = Color.White, fontSize = 6.sp)
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (currentUser != null){
        Button(
            onClick = {
                val occ = Occurrence(
                    uid = UUID.randomUUID().toString(),
                    name = nome,
                    type = tipo,
                    address = endereco,
                    description = descricao,
                    contact = contato,
                    picsUrl = fotosUrl,
                    userId = currentUser.uid
                )

                coroutineScope.launch {
                    try {
                        println(currentUser.uid)
                        dataSource.saveOccurrence(occ)
                        dataUserSource.addOccurrenceToUser(currentUser, occ)
                        println("Ocorrência salva com sucesso")

                        // Limpa os campos após salvar
                        nome = ""
                        tipo = "Cachorro"
                        endereco = ""
                        descricao = ""
                        contato = ""
                        fotosUrl = emptyList()
                        fotos = emptyList()

                    } catch (e: Exception) {
                        println("Erro ao salvar ocorrência: ${e.message}")
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Enviar", fontWeight = FontWeight.Bold)
        }
    }}
}
