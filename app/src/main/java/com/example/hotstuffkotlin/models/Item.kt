package com.example.hotstuffkotlin.models

data class Item(
    var itemId: Int = 1,
    var buildingId : Int = 1,
    var name : String = "",
    var quantity : Int = 1,
    var category : String? = null,
    var room : String? = null,
    var make : String? = null,
    var value : Double? = null,
    var imagePath : String? = null,
    var description : String? = null
)