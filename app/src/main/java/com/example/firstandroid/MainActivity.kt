package com.example.firstandroid

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.util.Log
import com.example.firstandroid.ui.login.LoginActivity

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content2_main.*
import java.lang.String

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        button2.text = "my_Button2"

        // 因为后执行，会覆盖下面的。
        val button = findViewById<Button>(R.id.button2)
        button.setOnClickListener {
            Log.d("test", "button click")
//            val intent = Intent(this, LoginActivity().javaClass)
            val intent = Intent(this, TestListActivity().javaClass)
            startActivity(intent)
        }

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
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
