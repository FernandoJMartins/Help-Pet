import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import coil.compose.AsyncImage
import com.example.helppet.model.Occurrence

@Composable
fun HomeScreen() {

    val coroutineScope = rememberCoroutineScope()
    val dataSource = FirebaseOccurrenceDataSource()
    var occurrences by remember { mutableStateOf<List<Occurrence>>(emptyList()) }

    val scrollState = rememberScrollState()

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
            .background(Color(0xFFF2F2F2))
            .padding(16.dp)
    ) {
        Text(
            text = "Animais Perdidos",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        if (occurrences.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 100.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                occurrences.forEach { occ ->
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(0.dp)) {
                            // Imagem no topo
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
                                if("@" in occ.contact){
                                    Text(
                                        text = "✉️ ${occ.contact}",
                                        fontSize = 13.sp,
                                        color = Color(0xFF9E9E9E)
                                    )
                                }
                                else {
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
            }
        }
    }
}
