package com.example.arlibappstest.domain.repository

import com.example.arlibappstest.domain.Drug
import com.example.arlibappstest.util.Resource
import kotlinx.coroutines.flow.Flow

interface MedicinesRepository {
    suspend fun getMedicines(): Flow<Resource<List<Drug>>>
}