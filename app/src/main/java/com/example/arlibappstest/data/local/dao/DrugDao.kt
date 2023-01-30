package com.example.arlibappstest.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.arlibappstest.data.local.DrugEntity

@Dao
interface DrugDao {
    @Query("SELECT * FROM DrugEntity")
    fun getDrugs(): List<DrugEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertDrugs(drugs: List<DrugEntity>): List<Long>

    @Query("Delete from DrugEntity")
    fun clearAll()
}