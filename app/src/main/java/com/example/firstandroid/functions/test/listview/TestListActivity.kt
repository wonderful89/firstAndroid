package com.example.firstandroid.functions.test.listview

import android.app.Activity
import android.content.Intent
import android.R as R2
import com.example.firstandroid.R as R
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.firstandroid.functions.test.ISSUE
import kotlinx.android.synthetic.main.activity_test_list.*

//import sun.jvm.hotspot.utilities.IntArray

class TestListActivity : AppCompatActivity() {

    val _tag = "TestListActivity"

    val a = R2.drawable.ic_dialog_email
    private val fruitList: MutableList<Fruit> = ArrayList()

    private fun initFruits() {
        for (i in 0..1) {
            val apple = Fruit(
                "Apple",
                R2.drawable.ic_dialog_email
            )
            fruitList.add(apple)
            val banana = Fruit(
                "Banana",
                R2.drawable.ic_btn_speak_now
            )
            fruitList.add(banana)
            val orange = Fruit(
                "Orange",
                R2.drawable.ic_input_add
            )
            fruitList.add(orange)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_list)

        var issues = ISSUE.values().map { i -> i.name }
//        val colorArrays = resources.getStringArray(R.array.testArray)

        val arrayAdapter2 = ArrayAdapter<String>(this, R.layout.simple_list_item_x1, issues)
//        val arrayAdapter2 = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, issues)

//        initFruits()
//        val arrayAdapter = FruitAdapter(fruitList as ArrayList<Fruit>, this)

        listView.adapter = arrayAdapter2
        listView.dividerHeight = 2

        listView.setOnItemClickListener { adapterView, view, position: Int, id: Long ->
            //            toast(item)
            var activity: Activity = Activity()
            when (adapterView.getItemAtPosition(position)) {
                ISSUE.list_activity_one.name -> {
                    Log.e(_tag, "test1")
                    activity = ListActivityOne()
                }
                ISSUE.list_activity_2.name -> {
                    activity = ListActivityTwo()
                    Log.e(_tag, "test2")
                }
                ISSUE.list_activity_3.name -> {
                    activity = ListActivityThree()
                    Log.e(_tag, "test3")
                }
                else -> Log.e(_tag, "other")
            }

            val intent = Intent(this, activity.javaClass)
            startActivity(intent)
        }
    }
}

//class FruitAdapter(items: ArrayList<Fruit>, ctx: Context) :
//    ArrayAdapter<Fruit>(ctx, R.layout.content_list_item_test, items) {
//    fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
//        val fruit = getItem(i)
//        val view: View = LayoutInflater.from(context).inflate(resourceId, parent, false)
//        val fruitImage: ImageView = view.findViewById(R.id.fruit_image) as ImageView
//        val fruitName = view.findViewById(R.id.fruit_name) as TextView
//        fruitImage.setImageResource(fruit!!.imageId)
//        fruitName.text = fruit.name
//        return view
//    }
//
//}

class Fruit(val name: String, val imageId: Int)

