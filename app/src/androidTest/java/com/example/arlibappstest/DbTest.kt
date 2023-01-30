package com.example.arlibappstest

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.arlibappstest.data.local.DrugEntity
import com.example.arlibappstest.data.local.dao.DrugDao
import com.example.arlibappstest.data.local.db.ArllibDatabase
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class DbTest {
    private lateinit var dao: DrugDao
    private lateinit var db: ArllibDatabase

    @Before
    fun init() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, ArllibDatabase::class.java
        ).build()
        dao = db.drugDao()
    }

    @Test
    @Throws(Exception::class)
    fun writeDrugAndReadInList() {
        val drug = DrugEntity("1", "drug name", "500mg")
        dao.insertDrugs(listOf(drug))
        val drugs = dao.getDrugs()
        assertThat(drugs[0], equalTo(drug))
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }
}