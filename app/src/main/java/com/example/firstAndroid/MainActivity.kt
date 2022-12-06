package com.example.firstAndroid

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.firstAndroid.databinding.ActivityMainBinding
import com.example.firstAndroid.databinding.Content2MainBinding
import com.google.android.material.snackbar.Snackbar
import xiaofei.library.hermes.Hermes
import javax.inject.Singleton

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var contentBinding: Content2MainBinding

    var repository = Repository("ButtonTT",
        "Mladen Rakonjac", 1000, true)

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.w("MainActivity", "onCreate 方法调用")
        println("onCreate 方法调用");
//        setContentView(R.layout.activity_main)

//        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//        binding.root.button2.text = "myButton2"
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.repository = repository
//        binding.executePendingBindings()
        contentBinding = binding.content2
        contentBinding.button2.text = "myButton2"
        contentBinding.button2.setOnClickListener {
            Log.d("test", "button click")
//            val intent = Intent(this@MainActivity, TestListActivity().javaClass)
//            startActivity(intent)
        }
//
//        setSupportActionBar(binding.toolbar)
        contentBinding.text1.setOnClickListener {
            Log.d("tag", "text1 click")
        }
        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            Log.d("tag", "message")
//            val shoppingList = mutableListOf("catfish", "water", "tulips", "blue paint")
//            print(shoppingList[32])
        }

        binding.fab.setOnLongClickListener {
            Log.w("tag", "aaa: ${binding.fab.tooltipText.toString()}")
            true
//            return@setOnLongClickListener true
        }
    }

    fun button2OnClick(view: View) {
        Log.d("test", "button2OnClick: $view")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
