package com.example.arlibappstest.data.repository

import android.util.Log
import com.example.arlibappstest.R
import com.example.arlibappstest.data.local.LocalDataSource
import com.example.arlibappstest.data.model.asDrugs
import com.example.arlibappstest.data.remote.RemoteDataSource
import com.example.arlibappstest.domain.Drug
import com.example.arlibappstest.domain.asEntity
import com.example.arlibappstest.domain.asExternalModel
import com.example.arlibappstest.domain.repository.MedicinesRepository
import com.example.arlibappstest.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MedicinesRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : MedicinesRepository {
    override suspend fun getMedicines(isRefresh: Boolean): Flow<Resource<List<Drug>>> = flow {
        emit(Resource.Loading(true))
        if (isRefresh) {
            val response = remoteDataSource.getMedicines()
            if (response.isSuccessful) {
                val drugs = response.body()?.problems?.asDrugs() ?: emptyList()
                localDataSource.clearAll()
                localDataSource.saveDrugs(drugs.map { it.asEntity() })
                emit(Resource.Success(drugs))
            } else {
                Log.d("MedicinesRepository_TAG", "getMedicines: ${response.errorBody()}")
                emit(Resource.Error(R.string.failed_to_get_data))
            }
        } else {
            val drugs = localDataSource.getAllDrugs()
            if (drugs.isEmpty()) {
                getMedicines(true)
            } else {
                emit(Resource.Success(drugs.map { it.asExternalModel() }))
            }
        }
        emit(Resource.Loading(false))
    }

}