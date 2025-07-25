package com.example.helppet.data.firebase

import com.example.helppet.data.repository.IOccurrenceDataSource
import com.example.helppet.model.Occurrence
import com.example.helppet.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseOccurrenceDataSource : IOccurrenceDataSource{
    private val db = FirebaseFirestore.getInstance()

    override fun saveOccurrence(occurrence: Occurrence) {
        db.collection("occurrences")
            .add(occurrence)
            .addOnSuccessListener{
                System.out.println("Salvo com ID: ${it.id}")
            }
        .addOnFailureListener { e ->
            System.out.println(e)
        };
    }

    override suspend fun getOccurrences(): List<Occurrence> {
        val snapshot = db.collection("occurrences").get().await()
        return snapshot.documents.mapNotNull { it.toObject(Occurrence::class.java) }

    }
}

