package com.example.helppet.clientservice

import androidx.compose.runtime.MutableState
import com.example.helppet.model.Occurrence
import com.example.helppet.model.User

interface UserDao {

    suspend fun createUser(user: User)

    suspend fun addOccurrenceToUser(user: User, occurrence: Occurrence, userState: MutableState<User?>)

    suspend fun getUser(user:User): User?

    suspend fun login(email: String, pass: String): Boolean

}
