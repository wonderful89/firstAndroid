package com.example.firstAndroid.functions.ui.layout_test_1

import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.example.firstAndroid.R
import com.example.firstAndroid.base.BaseActivity
import kotlinx.android.synthetic.main.activity_layout_test2.*
import kotlinx.android.synthetic.main.fragment_test1_in_test_activity.*

class LayoutTest2Activity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_test2)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.hide(test1_fragment2)
        transaction.commit()

        test1_fragment1.look_demo.setOnClickListener {
            val transaction2 = supportFragmentManager.beginTransaction()
            transaction2.hide(test1_fragment1)
            transaction2.show(test1_fragment2)
            transaction2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            transaction2.commit()
        }
    }
}


