package com.example.arlibappstest.data.remote

import com.example.arlibappstest.data.model.ProblemsResponse
import retrofit2.Response

interface RemoteDataSource {
    suspend fun getMedicines(): Response<ProblemsResponse>
}