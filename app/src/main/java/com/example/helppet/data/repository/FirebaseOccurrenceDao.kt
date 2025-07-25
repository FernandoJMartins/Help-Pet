package com.example.helppet.data.repository

import android.util.Log
import com.example.helppet.model.Occurrence
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseOccurrenceDao{
    private val db = FirebaseFirestore.getInstance()
    private val collection = "occurrences"

    suspend fun saveOccurrence(occurrence: Occurrence) {
        try {
            occurrence.uid?.let {
                db.collection(collection)
                    .document(it).set(occurrence).await()
            }
        } catch (e: Exception) {
            Log.e("FirebaseAuth", "Erro ao cadastrar Ocorrência", e)
            throw e
        }
    }

    suspend fun getOccurrences(): List<Occurrence> {
        val snapshot = db.collection("occurrences").get().await()
        return snapshot.documents.mapNotNull { it.toObject(Occurrence::class.java) }

    }
}

