package com.example.firstAndroid.functions.ui.layout_test_1

import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.example.firstAndroid.R
import com.example.firstAndroid.R.id.test1_fragment1
import com.example.firstAndroid.base.BaseActivity
import com.example.firstAndroid.databinding.ActivityLayoutTest2Binding

class LayoutTest2Activity : BaseActivity() {

    private lateinit var binding: ActivityLayoutTest2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_test2)
//        binding = ActivityLayoutTest2Binding.inflate(layoutInflater)
//
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.hide(test1Fragment2)
//        transaction.commit()
//
//        binding.test1Fragment1.look_demo.setOnClickListener {
//            val transaction2 = supportFragmentManager.beginTransaction()
//            transaction2.hide(test1_fragment1)
//            transaction2.show(test1_fragment2)
//            transaction2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//            transaction2.commit()
//        }
    }
}


