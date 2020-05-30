package com.example.firstAndroid
//import android.R as R2
//import com.example.firstAndroid.R as R
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.firstAndroid.functions.components.ComponentActivity
import com.example.firstAndroid.functions.logic.LogicListActivity
import com.example.firstAndroid.functions.ui.UITestListActivity
import com.example.firstAndroid.functions.util.UtilTestActivity
import kotlinx.android.synthetic.main.activity_test_list.*

class TestListActivity : AppCompatActivity() {
    companion object {
        const val tag = "TestList"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_list)

        val lists = MainTest.values().map { item -> item.title }
        val arrayAdapter2 = ArrayAdapter(this, R.layout.item_simple_list_0, lists)
        listView.adapter = arrayAdapter2
        listView.dividerHeight = 2
        listView.setSelector(R.drawable.listview_selector_0)

        listView.setOnItemClickListener { adapterView, view, position: Int, id: Long ->
            val selectedItem = adapterView.getItemAtPosition(position)
            Log.w(tag, "view = $view")
            Log.w(tag, "selectItem = $selectedItem")
            var intentClass: Class<AppCompatActivity>? = null
            intentClass = when (position) {
                MainTest.UI.ordinal -> UITestListActivity().javaClass
                MainTest.Logic.ordinal -> LogicListActivity().javaClass
                MainTest.Util.ordinal -> UtilTestActivity().javaClass
                MainTest.Component.ordinal -> ComponentActivity().javaClass
                else -> MainActivity().javaClass
            }

            val intent = android.content.Intent(this, intentClass)
            startActivity(intent)
        }
    }
}
