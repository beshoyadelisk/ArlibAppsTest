package com.example.arlibappstest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.arlibappstest.data.repository.UserRepositoryImpl
import com.example.arlibappstest.domain.repository.UserRepository
import com.example.arlibappstest.util.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private lateinit var userRepository: UserRepository

    @Before
    fun init() {
        userRepository = UserRepositoryImpl()
    }

    @Test
    fun testLoginSuccess() {
        val result = runBlocking {
            userRepository.login("user", "pass").first()
        }
        Assert.assertNotNull(result.data)
        Assert.assertEquals(result.data!!.username, "user")
        Assert.assertEquals(result.data!!.password, "pass")
    }
}