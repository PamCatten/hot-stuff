package com.example.hotstuffkotlin.models

data class Building(
    var buildingId: Int = 1,
    var name: String = "",
    var type: String = "",
    var description: String? = null
)