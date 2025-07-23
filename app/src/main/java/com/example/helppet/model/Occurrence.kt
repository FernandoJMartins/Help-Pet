package com.example.helppet.model

data class Occurrence(
    var id: String? = null,
    var name: String = "",
    var type: String = "",
    var address: String = "",
    var description: String = "",
    var contact: String = "",
    var picsUrl: List<String> = emptyList()
)