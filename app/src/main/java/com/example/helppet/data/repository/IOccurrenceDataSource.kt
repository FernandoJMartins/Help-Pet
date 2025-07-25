package com.example.helppet.data.repository

import com.example.helppet.model.Occurrence
import com.example.helppet.model.User

interface IOccurrenceDataSource {
    fun saveOccurrence(occurrence: Occurrence)

    suspend fun getOccurrences() : List<Occurrence>

}