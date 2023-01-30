package com.example.arlibappstest.data.remote

import com.example.arlibappstest.data.model.ProblemsResponse
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val api: ArLibApi
) : RemoteDataSource {
    override suspend fun getMedicines(): Response<ProblemsResponse> = api.getMedications()
}