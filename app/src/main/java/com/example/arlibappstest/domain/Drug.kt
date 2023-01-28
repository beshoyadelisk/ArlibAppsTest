package com.example.arlibappstest.domain

import com.example.arlibappstest.data.model.AssociatedDrug

data class Drug(
    val dose: String,
    val name: String,
    val strength: String
)

fun AssociatedDrug.asExternalModel() = Drug(dose, name, strength)