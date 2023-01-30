package com.example.arlibappstest.data.local

import com.example.arlibappstest.data.local.dao.DrugDao
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val drugDao: DrugDao) : LocalDataSource {
    override suspend fun saveDrugs(drugs: List<DrugEntity>) {
        drugDao.insertDrugs(drugs)
    }

    override suspend fun getAllDrugs(): List<DrugEntity> = drugDao.getDrugs()

    override suspend fun clearAll() = drugDao.clearAll()
}