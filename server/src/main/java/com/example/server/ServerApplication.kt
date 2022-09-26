package com.example.server

import android.app.Application
import android.util.Log

class ServerApplication: Application() {
    companion object {
        const val tag = "ServerApplication"
    }
    override fun onCreate() {
        super.onCreate()

        Log.e(tag,"process info = ")
    }
}