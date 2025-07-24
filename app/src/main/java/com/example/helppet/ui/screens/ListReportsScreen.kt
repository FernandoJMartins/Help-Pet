import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import coil.compose.rememberAsyncImagePainter
import com.example.helppet.model.Occurrence
import kotlinx.coroutines.launch


import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll


@Composable
fun ListReportsScreen() {
    val coroutineScope = rememberCoroutineScope()
    val dataSource = FirebaseOccurrenceDataSource()
    var occurrences by remember { mutableStateOf<List<Occurrence>>(emptyList()) }

    val scrollState = rememberScrollState()


    // Load data only once
    LaunchedEffect(Unit) {
        try {
            occurrences = dataSource.getOccurrences()
            println("Ocorrência carregada com sucesso")
        } catch (e: Exception) {
            println("Erro ao carregar ocorrências: ${e.message}")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        Text(
            text = "Lista de Ocorrências",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        if (occurrences.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                occurrences.forEach { occ ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "Nome: ${occ.name}", fontWeight = FontWeight.Bold)
                            Text(text = "Tipo: ${occ.type}")
                            Text(text = "Descrição: ${occ.description}")

                            Spacer(modifier = Modifier.height(12.dp))

                            if (occ.picsUrl.isNotEmpty()) {
                                AsyncImage(
                                    model = occ.picsUrl.first(),
                                    contentDescription = "Imagem da ocorrência",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
