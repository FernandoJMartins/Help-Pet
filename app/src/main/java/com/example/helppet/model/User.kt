package com.example.helppet.model

data class User(
    var uid: String = "",
    var name: String = "",
    var email: String = "",
    var pass: String = "",
    var occSolved: List<Occurrence> = emptyList(),
    var occCreated: List<Occurrence> = emptyList()
) {

    companion object {
        private var _currentUser: User? = null

        val currentUser: User?
            get() = _currentUser


        fun login(user: User) {
            _currentUser = user
        }

        fun logout() {
            _currentUser = null
        }

    }
}
