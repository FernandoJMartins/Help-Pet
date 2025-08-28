package com.example.helppet.viewmodels

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.helppet.data.repository.FirebaseUserDao
import com.example.helppet.model.Occurrence
import com.example.helppet.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


sealed class UserUIState {
    object Idle: UserUIState()
    data class Success(val data: User) : UserUIState()
    data class Error(val msg : String? = null) : UserUIState()
    object Loading : UserUIState()
}

class UserViewModel : ViewModel()  {
    private val repository = FirebaseUserDao()

    private val _uiState = MutableStateFlow<UserUIState>(UserUIState.Idle)
    val uiState: StateFlow<UserUIState> = _uiState


    fun createUser(user : User) {
        viewModelScope.launch {
            _uiState.value = UserUIState.Loading
            try {
                repository.createUser(user)
                _uiState.value = UserUIState.Success(user)
            }
            catch (e : Exception){
                _uiState.value = UserUIState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }

    fun getUser(user: User) {
        viewModelScope.launch {
            _uiState.value = UserUIState.Loading
            try {
                val result = repository.getUser(user)
                if (result != null) {
                    _uiState.value = UserUIState.Success(result)
                } else {
                    _uiState.value = UserUIState.Error("Usuário não encontrado")
                }
            } catch (e: Exception) {
                _uiState.value = UserUIState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }

    fun addOccurrenceToUser(user: User, occurrence: Occurrence, userState: MutableState<User?>) {
        viewModelScope.launch {
            _uiState.value = UserUIState.Loading
            try {
                repository.addOccurrenceToUser(user, occurrence, userState)
                val updatedUser = userState.value
                if (updatedUser != null) {
                    _uiState.value = UserUIState.Success(updatedUser)
                } else {
                    _uiState.value = UserUIState.Error("Usuário não encontrado após atualização")
                }
            } catch (e: Exception) {
                _uiState.value = UserUIState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }
}


