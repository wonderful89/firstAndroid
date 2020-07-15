package com.example.firstAndroid.functions.ui.dagger

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.Component
import java.lang.reflect.Constructor
import javax.inject.Inject

class Dagger2Activity @Inject constructor() : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
