package com.example.arlibappstest.data.repository

import com.example.arlibappstest.R
import com.example.arlibappstest.data.model.User
import com.example.arlibappstest.domain.repository.UserRepository
import com.example.arlibappstest.util.Resource
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl : UserRepository {
    override suspend fun login(userName: String, password: String) = flow {
        if (userName.isEmpty() || password.isEmpty()) {
            emit(Resource.Error(R.string.empty_data))
        } else {
            emit(Resource.Success(User(userName, password)))
        }
    }
}