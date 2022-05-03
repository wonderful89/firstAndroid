package com.example.firstAndroid.functions.ui.layout_test_1

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import com.example.firstAndroid.R
import com.example.firstAndroid.base.BaseActivity
import com.example.firstAndroid.databinding.ActivityLayoutTest2Binding
import com.example.firstAndroid.databinding.FragmentTest1InTestActivityBinding

class LayoutTest2Activity : BaseActivity() {

    private lateinit var binding: ActivityLayoutTest2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_layout_test2)
        binding = ActivityLayoutTest2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val transaction = supportFragmentManager.beginTransaction()
        val fragment2 = supportFragmentManager.findFragmentById(R.id.test1_fragment2)!!
        val fragment1 = supportFragmentManager.findFragmentById(R.id.test1_fragment1)!!
        transaction.hide(fragment2)
        transaction.commit()

        val test1FragmentBinding = FragmentTest1InTestActivityBinding.bind(fragment1.requireView())

        /**
         * 原始的绑定方式
         * fragment1.view?.findViewById<TextView>(R.id.look_demo)?.setOnClickListener{}
         */
        test1FragmentBinding.lookDemo.setOnClickListener {
            Log.i(tag, "lookDemo click")
            val transaction2 = supportFragmentManager.beginTransaction()
            transaction2.hide(fragment1)
            transaction2.show(fragment2)
            transaction2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            transaction2.commit()
        }
    }
}


