package com.example.firstAndroid.functions.logic.testHermes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.firstAndroid.R
import com.example.firstAndroid.base.BaseActivity
import xiaofei.library.hermes.Hermes
import xiaofei.library.hermes.HermesListener

@Route(path = "/logic/hermesTest")
class HermesTestActivity : BaseActivity() {
    companion object {
        val tag = "HermesTestActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tag, "onCreate")
        setContentView(R.layout.activity_hermes_test)
        testMM()

        findViewById<View>(R.id.getValueBtn).setOnClickListener {
            val singleton = Hermes.getInstance(ISingleton::class.java)
            if (singleton != null) {
                Log.d(tag, "value:" + singleton.getValue())
            }
        }
    }

    private fun testMM() {
        //连接成功的监听
        Log.d(tag, "in testMM: connect")
        Hermes.setHermesListener(object : HermesListener() {
            override fun onHermesConnected(service: Class<out HermesService?>) {
                Log.d(tag, "链接成功")
            }
        })
        //连接
        Hermes.connect(applicationContext)
    }

    override fun onDestroy() {
        Hermes.disconnect(applicationContext)
        super.onDestroy()
    }
}