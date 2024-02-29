package com.hotstuff.models

data class Item (
    var id: Int = 1,
    var buildingId : Int = 1,
    var name : String = "",
    var quantity : Int = 1,
    var category : String? = null,
    var room : String? = null,
    var make : String? = null,
    var value : Double? = null,
    var imageUri : String? = null,
    var description : String? = null
)