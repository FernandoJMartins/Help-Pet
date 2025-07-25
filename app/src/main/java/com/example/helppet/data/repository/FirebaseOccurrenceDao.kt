package com.example.helppet.data.repository

import android.util.Log
import com.example.helppet.model.Occurrence
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseOccurrenceDao{
    private val db = FirebaseFirestore.getInstance()

    fun saveOccurrence(occurrence: Occurrence) {
        db.collection("occurrences")
            .add(occurrence)
            .addOnSuccessListener{
                Log.e("FirebaseOccurrence", "Occurrence Saved")
            }
        .addOnFailureListener { e ->
            Log.e("FirebaseOccurrence", "Error while saving Occurrence", e)
        };
    }

    suspend fun getOccurrences(): List<Occurrence> {
        val snapshot = db.collection("occurrences").get().await()
        return snapshot.documents.mapNotNull { it.toObject(Occurrence::class.java) }

    }
}

