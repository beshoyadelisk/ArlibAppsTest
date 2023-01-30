package com.example.arlibappstest.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.arlibappstest.data.local.DrugEntity
import com.example.arlibappstest.data.local.dao.DrugDao


@Database(entities = [DrugEntity::class], version = 1, exportSchema = true)
abstract class ArllibDatabase : RoomDatabase() {
    abstract fun drugDao(): DrugDao
}