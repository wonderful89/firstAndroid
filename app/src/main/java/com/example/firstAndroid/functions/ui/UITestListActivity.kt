package com.example.firstAndroid.functions.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.firstAndroid.MainActivity
import com.example.firstAndroid.MessageEvent
import com.example.firstAndroid.R
import com.example.firstAndroid.base.BaseActivity
import com.example.firstAndroid.databinding.ActivityTestListBinding
import com.example.firstAndroid.functions.ui.animation.UIAnimate1Activity
import com.example.firstAndroid.functions.ui.animation.UIAnimate2Activity
import com.example.firstAndroid.functions.ui.anko.Anko1Activity
import com.example.firstAndroid.functions.ui.anko.Anko2Activity
import com.example.firstAndroid.functions.ui.customview.CustomViewActivity
import com.example.firstAndroid.functions.ui.dagger.Dagger1Activity
import com.example.firstAndroid.functions.ui.layout_test_1.LayoutTest1Activity
import com.example.firstAndroid.functions.ui.layout_test_1.LayoutTest2Activity
import com.example.firstAndroid.functions.ui.login.LoginActivity
import com.example.firstAndroid.functions.ui.touchMe.UITouchMeActivity
import org.greenrobot.eventbus.EventBus
import java.util.logging.Logger

class UITestListActivity : BaseActivity() {

    companion object {
        const val tag = "UITestList"

        //        val log: Logger = Logger.getLogger(this.javaClass.name)
        val log: Logger = Logger.getLogger(this::class.java.name)
        private lateinit var binding: ActivityTestListBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_test_list)
        binding = ActivityTestListBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        title = this.javaClass.simpleName //"UI测试"

        val lists = ListViewTest.values().map { item -> item.title }
        val arrayAdapter2 = ArrayAdapter(this, R.layout.item_simple_list_0, lists)
        binding.listView.adapter = arrayAdapter2
        binding.listView.dividerHeight = 2
        binding.listView.setSelector(R.drawable.listview_selector_0)

        EventBus.getDefault().post(MessageEvent("message1", code = 200))

        binding.listView.setOnItemClickListener { _, _, position: Int, _: Long ->
            var intentActivity: AppCompatActivity? = null
            intentActivity = when (position) {
                ListViewTest.TestView1.ordinal -> UITestView1Activity()
                ListViewTest.TouchMe.ordinal -> UITouchMeActivity()
                ListViewTest.Animate1.ordinal -> UIAnimate1Activity()
                ListViewTest.Animate2.ordinal -> UIAnimate2Activity()
                ListViewTest.AnKoTest1.ordinal -> Anko1Activity()
                ListViewTest.AnKoTest2.ordinal -> Anko2Activity()
                ListViewTest.DaggerTest1.ordinal -> Dagger1Activity()
                ListViewTest.Login.ordinal -> LoginActivity()
                ListViewTest.LayoutTest1.ordinal -> LayoutTest1Activity()
                ListViewTest.LayoutTest2.ordinal -> LayoutTest2Activity()
                ListViewTest.CustomView.ordinal -> CustomViewActivity()
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