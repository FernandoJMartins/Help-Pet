package com.example.helppet.data.repository

import android.util.Log
import com.example.helppet.model.Occurrence
import com.example.helppet.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseAuthenticationDao {
    private val db = FirebaseFirestore.getInstance()
    private val collection = "users"

    suspend fun createUser(user: User) {
        try {
            val data = mapOf(
                "id" to user.id,
                "name" to user.name,
                "email" to user.email,
                "pass" to user.pass,
                "occSolved" to user.occSolved.map { it.toMap() },
                "occCreated" to user.occCreated.map { it.toMap() }
            )
            db.collection(collection).add(data).await()
        } catch (e: Exception) {
            Log.e("FirebaseAuth", "Erro ao cadastrar usuário", e)
            throw e
        }
    }

    fun updateUser(user: User){
        try {
            db.collection(collection).document(user.id).set(user)
        } catch (e: Exception) {
            Log.e("FirebaseAuth", "Erro ao atualizar usuário", e)
            throw e
        }
    }

    suspend fun login(email: String, pass: String): Boolean {
        return try {
            val result = db.collection(collection)
                .whereEqualTo("email", email)
                .whereEqualTo("pass", pass)
                .get()
                .await()

            if (result.isEmpty) return false

            val doc = result.documents.first()

            val occSolvedList = doc.toOccurrenceList("occSolved")
            val occCreatedList = doc.toOccurrenceList("occCreated")

            val user = User(
                id = doc.getString("id") ?: "",
                name = doc.getString("name") ?: "",
                email = doc.getString("email") ?: "",
                pass = doc.getString("pass") ?: "",
                occSolved = occSolvedList,
                occCreated = occCreatedList
            )

            User.login(user)
            true
        } catch (e: Exception) {
            Log.e("FirebaseAuth", "Erro ao fazer login", e)
            false
        }
    }

    private fun Occurrence.toMap(): Map<String, Any?> = mapOf(
        "userId" to userId,
        "id" to id,
        "name" to name,
        "type" to type,
        "address" to address,
        "description" to description,
        "contact" to contact,
        "picsUrl" to picsUrl
    )


    private fun com.google.firebase.firestore.DocumentSnapshot.toOccurrenceList(field: String): List<Occurrence> {
        return (get(field) as? List<Map<String, Any?>>)?.map {
            Occurrence(
                userId = it["userId"] as? String ?: "",
                id = it["id"] as? String,
                name = it["name"] as? String ?: "",
                type = it["type"] as? String ?: "",
                address = it["address"] as? String ?: "",
                description = it["description"] as? String ?: "",
                contact = it["contact"] as? String ?: "",
                picsUrl = it["picsUrl"] as? List<String> ?: emptyList()
            )
        } ?: emptyList()
    }

}
