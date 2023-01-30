package com.example.arlibappstest.data.model

import com.example.arlibappstest.domain.asExternalModel

data class ProblemsResponse(
    val problems: List<Problem>
)

fun List<Problem>.asDrugs() = flatMap { it.Diabetes }
    .flatMap { it.medications }
    .flatMap { it.medicationsClasses }
    .flatMap { it.className2 + it.className }
    .flatMap { it.associatedDrug + it.associatedDrug2 }
    .map { it.asExternalModel() }
