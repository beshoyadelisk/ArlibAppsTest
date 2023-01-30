package com.example.arlibappstest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.arlibappstest.data.remote.ArLibApi
import com.example.arlibappstest.data.remote.RemoteDataSource
import com.example.arlibappstest.data.remote.RemoteDataSourceImpl
import com.example.arlibappstest.util.MockResponseFileReader
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSourceTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val server = MockWebServer()
    private lateinit var remoteDataSource: RemoteDataSource
    private lateinit var mockedResponse: String
    private val gson = GsonBuilder().setLenient().create()

    @Before
    fun init() {
        server.start(8000)
        val BASE_URL = server.url("/").toString()

        val okHttpClient = OkHttpClient
            .Builder()
            .build()

        val service = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
            .create(ArLibApi::class.java)
        remoteDataSource = RemoteDataSourceImpl(service)
    }

    @Test
    fun testApiSuccess() {
        mockedResponse = MockResponseFileReader("arlbDetailsApi/success.json").content
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockedResponse)
        )
        val response = runBlocking { remoteDataSource.getMedicines() }
        val json = gson.toJson(response.body())
        val resultResponse = JsonParser.parseString(json)
        val expectedResponse = JsonParser.parseString(mockedResponse)
        Assert.assertNotNull(response)
        Assert.assertEquals(resultResponse, expectedResponse)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}