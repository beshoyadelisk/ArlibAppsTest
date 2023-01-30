package com.example.arlibappstest.data.local

import com.example.arlibappstest.domain.Drug

interface LocalDataSource {
    suspend fun saveDrugs(drugs:List<DrugEntity>)
    suspend fun getAllDrugs():List<DrugEntity>
    suspend fun clearAll()
}