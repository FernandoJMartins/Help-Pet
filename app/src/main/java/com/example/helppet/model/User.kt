package com.example.helppet.model

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val pass: String = "",
    val occSolved: List<Occurrence> = emptyList(),
    val occCreated: List<Occurrence> = emptyList()
) {

    companion object {
        private var _currentUser: User? = null

        val currentUser: User?
            get() = _currentUser


        fun login(user: User) {
            _currentUser = user
        }

        fun getLoggedUser(): User? {
            return _currentUser
        }

        fun logout() {
            _currentUser = null
        }

        fun isLoggedIn(): Boolean {
            return _currentUser != null
        }

        fun addCreatedOccurrence(occ: Occurrence) {
            _currentUser = _currentUser?.copy(
                occCreated = _currentUser?.occCreated.orEmpty() + occ
            )
        }

    }
}
