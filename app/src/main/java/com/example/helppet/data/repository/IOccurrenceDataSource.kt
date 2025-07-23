package com.example.helppet.data.repository

import com.example.helppet.model.Occurrence

interface IOccurrenceDataSource {
    fun saveOccurrence(occurrence: Occurrence)

    suspend fun getOccurrences() : List<Occurrence>

}