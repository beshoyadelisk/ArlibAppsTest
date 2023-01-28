package com.example.arlibappstest.data.repository

import com.example.arlibappstest.data.model.User
import com.example.arlibappstest.domain.repository.UserRepository
import com.example.arlibappstest.util.Resource
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl : UserRepository {
    override suspend fun login(userName: String, password: String) = flow {
        emit(Resource.Loading(true))
        emit(Resource.Success(User(userName, password)))
        emit(Resource.Loading(false))
    }
}