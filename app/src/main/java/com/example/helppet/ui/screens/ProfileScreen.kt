package com.example.helppet.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.helppet.model.Occurrence
import com.example.helppet.model.User

@Composable
fun ProfileScreen(
    onLogout: () -> Unit
) {
    val currentUser = User.currentUser

    Surface(modifier = Modifier.fillMaxSize()) {
        if (currentUser == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Usuário não logado")
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(Color(0xFFF2F2F2))
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Perfil do Usuário",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 12.dp),
                    style = MaterialTheme.typography.headlineMedium)

                Spacer(modifier = Modifier.height(24.dp))

                Text(currentUser.name,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 12.dp),
                    style = MaterialTheme.typography.bodyLarge)

                Spacer(modifier = Modifier.height(8.dp))

                Text("Email: ${currentUser.email}", style = MaterialTheme.typography.bodyLarge)

                Spacer(modifier = Modifier.height(24.dp))
                Divider()
                Spacer(modifier = Modifier.height(16.dp))


                Text("Ocorrências Criadas", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))

                if (currentUser.occCreated.isEmpty()) {
                    Text("Nenhuma ocorrência criada ainda")
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        currentUser.occCreated.forEach { occ ->
                            OccurrenceCard(occ)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                Divider()
                Spacer(modifier = Modifier.height(16.dp))


                Text("Ocorrências Resolvidas", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))

                if (currentUser.occSolved.isEmpty()) {
                    Text("Nenhuma ocorrência resolvida ainda")
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        currentUser.occSolved.forEach { occ ->
                            OccurrenceCard(occ)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
                Divider()
                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    User.logout()
                    onLogout()
                }) {
                    Text("Sair")
                }
            }
        }
    }
}

@Composable
fun OccurrenceCard(occ: Occurrence) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(0.dp)) {
            // Imagem
            if (occ.picsUrl.isNotEmpty()) {
                AsyncImage(
                    model = occ.picsUrl.first(),
                    contentDescription = "Imagem da ocorrência",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                )
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = occ.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF333333)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = occ.type,
                    fontSize = 14.sp,
                    color = Color(0xFF757575)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = occ.description,
                    fontSize = 14.sp,
                    color = Color(0xFF616161)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "📍 ${occ.address}",
                    fontSize = 13.sp,
                    color = Color(0xFF9E9E9E)
                )
                Spacer(modifier = Modifier.height(4.dp))
                if ("@" in occ.contact) {
                    Text(
                        text = "✉️ ${occ.contact}",
                        fontSize = 13.sp,
                        color = Color(0xFF9E9E9E)
                    )
                } else {
                    Text(
                        text = "📞 ${occ.contact}",
                        fontSize = 13.sp,
                        color = Color(0xFF9E9E9E)
                    )
                }
            }
        }
    }
}
