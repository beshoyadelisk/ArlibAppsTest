package com.example.arlibappstest

import android.app.Application
import com.example.arlibappstest.data.model.User
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ArlibApp : Application() {
    companion object {
        var currentUser: User? = null
    }
}