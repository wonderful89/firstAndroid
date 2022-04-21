package com.example.firstAndroid

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.firstAndroid.databinding.ActivityMainBinding
import com.qqz.baselib.QZBaseLib

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_u_i_touch_me.*
import kotlinx.android.synthetic.main.content2_main.*
import kotlinx.android.synthetic.main.content2_main.button2
import kotlinx.android.synthetic.main.content2_main.view.*
import org.jetbrains.anko.doAsync

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var repository = Repository("ButtonTT",
        "Mladen Rakonjac", 1000, true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.w("MainActivity", "onCreate 方法调用")
        println("onCreate 方法调用");
//        setContentView(R.layout.activity_main)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//        binding.root.button2.text = "myButton2"

        binding.repository = repository
        binding.executePendingBindings()

        binding.root.apply {
//            button2.text = "myButton2"
            // 因为后执行，会覆盖下面的。
            button2.setOnClickListener {
                Log.d("test", "button click")
                val intent = Intent(this@MainActivity, TestListActivity().javaClass)
                startActivity(intent)
            }

        }

        setSupportActionBar(toolbar)
        text1.setOnClickListener {
            Log.d("tag", "text1 click")
        }
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            Log.d("tag", "message")
//            val shoppingList = mutableListOf("catfish", "water", "tulips", "blue paint")
//            print(shoppingList[32])
        }


        fab.setOnLongClickListener {
            Log.w("tag", "aaa")
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
