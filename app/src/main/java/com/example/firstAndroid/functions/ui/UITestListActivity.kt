package com.example.firstAndroid.functions.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.firstAndroid.MainActivity
import com.example.firstAndroid.R
import com.example.firstAndroid.base.BaseActivity
import com.example.firstAndroid.functions.ui.animation.UIAnimate1Activity
import com.example.firstAndroid.functions.ui.animation.UIAnimate2Activity
import com.example.firstAndroid.functions.ui.anko.Anko1Activity
import com.example.firstAndroid.functions.ui.anko.Anko2Activity
import com.example.firstAndroid.functions.ui.login.LoginActivity
import com.example.firstAndroid.functions.ui.touchMe.UITouchMeActivity
import kotlinx.android.synthetic.main.activity_test_list.*
import java.util.logging.Logger

class UITestListActivity : BaseActivity() {

    companion object{
        const val tag = "UITestList"
//        val log: Logger = Logger.getLogger(this.javaClass.name)
        val log: Logger = Logger.getLogger(this::class.java.name)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_list)
//        title = this.javaClass.simpleName //"UI测试"

        val lists = ListViewTest.values().map { item -> item.title }
        val arrayAdapter2 = ArrayAdapter(this, R.layout.item_simple_list_0, lists)
        listView.adapter = arrayAdapter2
        listView.dividerHeight = 2
        listView.setSelector(R.drawable.listview_selector_0)

        listView.setOnItemClickListener { _, _, position: Int, _: Long ->
            var intentActivity: AppCompatActivity? = null
            intentActivity = when (position) {
                ListViewTest.TestView1.ordinal -> UITestView1Activity()
                ListViewTest.TouchMe.ordinal -> UITouchMeActivity()
                ListViewTest.Animate1.ordinal -> UIAnimate1Activity()
                ListViewTest.Animate2.ordinal -> UIAnimate2Activity()
                ListViewTest.AnKoTest1.ordinal -> Anko1Activity()
                ListViewTest.AnKoTest2.ordinal -> Anko2Activity()
                ListViewTest.Login.ordinal -> LoginActivity()
                else -> MainActivity()
            }

            val intent = android.content.Intent(this, intentActivity.javaClass)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        log.info("onStart")
    }

    override fun onPause() {
        super.onPause()
        log.info("onPause")
    }

    override fun onRestart() {
        super.onRestart()
        log.info("onRestart")
    }

    override fun onResume() {
        super.onResume()
        log.info("onResume")
    }

    override fun onStop() {
        super.onStop()
        log.info("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        log.info("onDestroy")
    }
}