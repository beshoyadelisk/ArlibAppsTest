package com.example.arlibappstest.di

import com.example.arlibappstest.data.remote.ArLibApi
import com.example.arlibappstest.data.remote.RemoteDataSource
import com.example.arlibappstest.data.remote.RemoteDataSourceImpl
import com.example.arlibappstest.data.repository.MedicinesRepositoryImpl
import com.example.arlibappstest.data.repository.UserRepositoryImpl
import com.example.arlibappstest.domain.repository.MedicinesRepository
import com.example.arlibappstest.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesUserRepository(): UserRepository = UserRepositoryImpl()

    @Provides
    @Singleton
    fun provideRemoteDataSource(api: ArLibApi):RemoteDataSource = RemoteDataSourceImpl(api)

    @Provides
    @Singleton
    fun providesMedicineRepository(remoteDataSource: RemoteDataSource):MedicinesRepository =
        MedicinesRepositoryImpl(remoteDataSource)

    @Provides
    @Singleton
    fun provideDispatcher(): CoroutineDispatcher = Dispatchers.IO
}