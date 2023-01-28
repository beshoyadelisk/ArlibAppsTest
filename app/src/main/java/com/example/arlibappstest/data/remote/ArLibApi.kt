package com.example.arlibappstest.data.remote

import com.example.arlibappstest.data.model.ProblemsResponse
import retrofit2.http.GET

interface ArLibApi {
    @GET("v3/140b2f50-4a9e-4695-90b3-76b19b4b83cc")
    suspend fun getMedications(): ProblemsResponse
}