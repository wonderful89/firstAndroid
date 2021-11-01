package com.example.firstAndroid.functions.logic.network

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.firstAndroid.R
import com.example.firstAndroid.base.BaseActivity

@Route(path = "/logic/network")
class NetworkMainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network_main)
        title = intent.getStringExtra("title")
        main()
    }
}