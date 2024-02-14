package com.example.hotstuffkotlin.models

data class Building(
    var id: Int = 1,
    var name: String = "",
    var type: String? = null,
    var description: String? = null
)