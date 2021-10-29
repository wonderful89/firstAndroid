package com.example.firstAndroid.functions.logic.network

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firstAndroid.R

class NetworkMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network_main)
        main()
    }
}