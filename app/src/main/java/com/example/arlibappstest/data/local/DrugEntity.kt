package com.example.arlibappstest.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(primaryKeys = ["dose","name","strength"])
data class DrugEntity(
    val dose: String,
    val name: String,
    val strength: String
)
