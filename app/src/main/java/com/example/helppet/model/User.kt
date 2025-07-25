package com.example.helppet.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class User(
    var uid: String = "",
    var name: String = "",
    var email: String = "",
    var pass: String = "",
    var occSolved: List<Occurrence> = emptyList(),
    var occCreated: List<Occurrence> = emptyList()
) {

    companion object {
        var _currentUser by mutableStateOf<User?>(null)

        var currentUser: User? = null
            get() = _currentUser


        fun login(user: User) {
            _currentUser = user
        }

        fun logout() {
            _currentUser = null
        }

    }
}
