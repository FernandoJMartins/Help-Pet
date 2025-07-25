package com.example.helppet.data.repository

import android.util.Log
import androidx.compose.runtime.MutableState
import com.example.helppet.model.Occurrence
import com.example.helppet.model.User
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseUserDao {
    private val db = FirebaseFirestore.getInstance()
    private val collection = "users"

    suspend fun createUser(user: User) {
        try {
            db.collection(collection).document(user.uid).set(user).await()
        } catch (e: Exception) {
            Log.e("FirebaseAuth", "Erro ao cadastrar usuário", e)
            throw e
        }
    }

    suspend fun addOccurrenceToUser(user: User, occurrence: Occurrence, userState: MutableState<User?>) {
        try {
            val occurrenceMap = occurrence.toMap().toMutableMap()

            db.collection(collection)
                .document(user.uid)
                .update("occCreated", FieldValue.arrayUnion(occurrenceMap))
                .await()

            val updatedUser = getUser(user) // atualiza o usuário local
            userState.value = updatedUser
            User.currentUser = updatedUser


        } catch (e: Exception) {
            Log.e("FirebaseAuth", "Erro ao adicionar ocorrência criada", e)
            throw e
        }
    }

    suspend fun getUser(user:User): User? {
        try {
            val snapshot = db.collection(collection).document(user.uid).get().await()
            return snapshot.toObject(User::class.java)
        } catch (e: Exception) {
            Log.e("FirebaseAuth", "Erro ao buscar usuario", e)
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

            val doc = result.documents.first() //tras a primeira Ocorrencia

            val occSolvedList = doc.toOccurrenceList("occSolved")
            val occCreatedList = doc.toOccurrenceList("occCreated")

            val user = User(
                uid = doc.getString("uid") ?: "",
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
        "uid" to uid,
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
                uid = it["uid"] as? String,
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
