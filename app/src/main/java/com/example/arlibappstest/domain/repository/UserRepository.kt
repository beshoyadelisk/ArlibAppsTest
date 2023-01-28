package com.example.arlibappstest.domain.repository

import com.example.arlibappstest.data.model.User
import com.example.arlibappstest.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun login(userName: String, password: String): Flow<Resource<User>>
}