package com.example.tiendaonline.model.entity

import androidx.room.PrimaryKey
import io.realm.kotlin.types.RealmObject

class Item : RealmObject {
    @PrimaryKey
    val _id: String = ""
    val name: String = ""
    val price: Double = 0.0
    val imageId: Int = 0
    val idProduct: Int = 0
    val quantity: Int = 0
}