import com.example.helppet.data.repository.IOccurrenceDataSource
import com.example.helppet.model.Occurrence
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseOccurrenceDataSource : IOccurrenceDataSource{
    private val db = FirebaseFirestore.getInstance()

    override fun saveOccurrence(occurrence: Occurrence) {
        db.collection("occurrences")
            .add(occurrence)
    }

    override suspend fun getOccurrences(): List<Occurrence> {
        val snapshot = db.collection("occurrences").get().await()
        return snapshot.documents.mapNotNull { it.toObject(Occurrence::class.java) }

    }
}

