package com.example.helppet.data.repository

import com.example.helppet.model.Occurrence

class OccurrenceRepository(private val dataSource: IOccurrenceDataSource){

    fun save(occurrence: Occurrence) {
        dataSource.saveOccurrence(occurrence)
    }

    suspend fun getAll() : List<Occurrence> {
        return dataSource.getOccurrences()
    }

}