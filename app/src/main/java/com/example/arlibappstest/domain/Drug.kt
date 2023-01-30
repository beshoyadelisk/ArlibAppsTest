package com.example.arlibappstest.domain

import android.os.Parcelable
import com.example.arlibappstest.data.local.DrugEntity
import com.example.arlibappstest.data.model.AssociatedDrug
import kotlinx.parcelize.Parcelize

@Parcelize
data class Drug(
    val dose: String,
    val name: String,
    val strength: String
) : Parcelable

fun AssociatedDrug.asExternalModel() = Drug(dose, name, strength)
fun DrugEntity.asExternalModel() = Drug(dose, name, strength)

fun Drug.asEntity() = DrugEntity(dose, name, strength)