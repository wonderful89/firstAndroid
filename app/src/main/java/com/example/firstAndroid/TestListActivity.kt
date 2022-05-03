package com.example.firstAndroid
//import android.R as R2
//import com.example.firstAndroid.R as R
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.firstAndroid.base.BaseActivity
import com.example.firstAndroid.databinding.ActivityTestListBinding
import com.example.firstAndroid.functions.components.ComponentActivity
import com.example.firstAndroid.functions.logic.LogicListActivity
import com.example.firstAndroid.functions.ui.UITestListActivity
import com.example.firstAndroid.functions.util.UtilTestActivity
import com.qqz.baselib.QZBaseLib
import com.qqz.baselib.QZBaseTool
//import kotlinx.android.synthetic.main.activity_test_list.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class TestListActivity : BaseActivity(), LifecycleObserver{
    companion object {
        const val tag = "TestList"
    }

    private lateinit var binding: ActivityTestListBinding

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onLifecyclePause() {
        Log.d(tag, "paused")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onLifecycleResume() {
        Log.d(tag, "resumed")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this);
//        setContentView(R.layout.activity_test_list)
        binding = ActivityTestListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val lists = MainTest.values().map { item -> item.title }
        val arrayAdapter2 = ArrayAdapter(this, R.layout.item_simple_list_0, lists)
        binding.listView.adapter = arrayAdapter2
        binding.listView.dividerHeight = 2
        binding.listView.setSelector(R.drawable.listview_selector_0)

//        QZBaseLib.printResolution(this)

        binding.listView.setOnItemClickListener { adapterView, view, position: Int, id: Long ->
            val selectedItem = adapterView.getItemAtPosition(position)
            Log.w(tag, "view = $view")
            Log.w(tag, "selectItem = $selectedItem")
            var intentActivity: AppCompatActivity? = null
            when(position) {
                MainTest.DensitySetting.ordinal -> {
//                    QZBaseLib.setDensityDpi(200)
                    QZBaseLib.magnifyTwoTimes()
                    QZBaseTool.recreateActivity(this, true)
                    return@setOnItemClickListener
                }
            }
            intentActivity = when (position) {
                MainTest.UI.ordinal -> UITestListActivity()
                MainTest.Logic.ordinal -> LogicListActivity()
                MainTest.Util.ordinal -> UtilTestActivity()
                MainTest.Component.ordinal -> ComponentActivity()
                MainTest.OriginMain.ordinal -> MainActivity()
                else -> MainActivity()
            }
            val intent = android.content.Intent(this, intentActivity.javaClass)
            startActivity(intent)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = false)
    fun onReceiveEventBus(messageEvent:MessageEvent) {
        Log.d("tag", "event: ${messageEvent.message}");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onReceiveEventBus2(messageEvent:MessageEvent) {
        Log.d("tag", "event22: ${messageEvent.message}");
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
}

class MessageEvent(val message: String, val code: Int) {

}