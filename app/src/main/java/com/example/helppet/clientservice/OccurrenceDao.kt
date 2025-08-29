package com.example.helppet.clientservice

import com.example.helppet.model.Occurrence

interface OccurrenceDao{
    suspend fun saveOccurrence(occurrence: Occurrence)
    suspend fun getOccurrences(): List<Occurrence>
}

