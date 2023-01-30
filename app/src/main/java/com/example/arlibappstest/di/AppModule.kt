package com.example.arlibappstest.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.arlibappstest.data.local.LocalDataSource
import com.example.arlibappstest.data.local.LocalDataSourceImpl
import com.example.arlibappstest.data.local.dao.DrugDao
import com.example.arlibappstest.data.local.db.ArllibDatabase
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
    fun provideArlibDb(app: Application): ArllibDatabase {
        return Room.databaseBuilder(app, ArllibDatabase::class.java, "ArllibDatabase.db")
            .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
            .build()
    }

    @Provides
    @Singleton
    fun provideDrugDao(db: ArllibDatabase): DrugDao = db.drugDao()

    @Provides
    @Singleton
    fun providesUserRepository(): UserRepository = UserRepositoryImpl()

    @Provides
    @Singleton
    fun provideRemoteDataSource(api: ArLibApi): RemoteDataSource = RemoteDataSourceImpl(api)

    @Provides
    @Singleton
    fun provideLocalDataSource(drugDao: DrugDao): LocalDataSource = LocalDataSourceImpl(drugDao)

    @Provides
    @Singleton
    fun providesMedicineRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): MedicinesRepository =
        MedicinesRepositoryImpl(remoteDataSource, localDataSource)

    @Provides
    @Singleton
    fun provideDispatcher(): CoroutineDispatcher = Dispatchers.IO
}