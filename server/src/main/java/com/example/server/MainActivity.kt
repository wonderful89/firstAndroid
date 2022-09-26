package com.example.server

import android.content.UriMatcher
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.server.databinding.ActivityMainBinding
import com.example.server.servers.CustomContentProvider
import com.example.server.util.ProcessInfo
import com.example.server.util.ProcessUtil

class MainActivity : AppCompatActivity() {
    companion object {
        const val tag = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        CustomContentProvider.testName = "change to new value"
        testMatch()

        ProcessUtil.getProcessListInfo(this)
    }

    private fun testMatch() {
        val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        sUriMatcher.addURI("test", "book", 0)
        Log.e(tag, "testMatch ${sUriMatcher.match(Uri.parse("content://test/book"))}")

        sUriMatcher.addURI("test", "user", 33)
        Log.e(tag, "testMatch ${sUriMatcher.match(Uri.parse("content://test/user"))}")
    }
}