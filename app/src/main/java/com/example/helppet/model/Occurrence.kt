package com.example.helppet.model

data class Occurrence(
    val name: String = "",
    val type: String = "",
    val address: String = "",
    val description: String = "",
    val contact: String = "",
    val picsUrl: List<String> = emptyList()
)