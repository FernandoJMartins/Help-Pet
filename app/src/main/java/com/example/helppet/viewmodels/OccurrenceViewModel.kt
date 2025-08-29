package com.example.helppet.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.helppet.data.repository.FirebaseOccurrenceDao
import com.example.helppet.model.Occurrence
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


sealed class OccurrenceUIState {
    object Idle: OccurrenceUIState()
    data class Success(val data: List<Occurrence>) : OccurrenceUIState()
    data class SuccessSave(val data: Occurrence) : OccurrenceUIState()
    data class Error(val msg : String? = null) : OccurrenceUIState()
    object Loading : OccurrenceUIState()
}

class OccurrenceViewModel : ViewModel()  {
    private val repository = FirebaseOccurrenceDao()

    private val _occurrences = MutableStateFlow<List<Occurrence>>(emptyList())
    val occurrences: StateFlow<List<Occurrence>> = _occurrences

    private val _uiState = MutableStateFlow<OccurrenceUIState>(OccurrenceUIState.Idle)
    val uiState: StateFlow<OccurrenceUIState> = _uiState

    fun getOccurrences() {
        viewModelScope.launch {
            _uiState.value = OccurrenceUIState.Loading
            try{
                val occurrences = repository.getOccurrences()
                _uiState.value = OccurrenceUIState.Success(occurrences)
            }
            catch (e : Exception){
                _uiState.value = OccurrenceUIState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }

    fun saveOccurrence(occurrence : Occurrence) {
        viewModelScope.launch {
            _uiState.value = OccurrenceUIState.Loading
            try {
                repository.saveOccurrence(occurrence)
                _uiState.value = OccurrenceUIState.SuccessSave(occurrence)
            }
            catch (e : Exception){
                _uiState.value = OccurrenceUIState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }
}


