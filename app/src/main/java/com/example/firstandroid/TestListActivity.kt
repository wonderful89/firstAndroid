package com.example.firstandroid

import android.content.Context
import android.R as R2
import com.example.firstandroid.R as R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_test_list.*
import org.jetbrains.anko.toast
//import sun.jvm.hotspot.utilities.IntArray


class TestListActivity : AppCompatActivity() {

    val a = R2.drawable.ic_dialog_email
    private val fruitList: MutableList<Fruit> = ArrayList()

    private fun initFruits() {
        for (i in 0..1) {
            val apple = Fruit("Apple", R2.drawable.ic_dialog_email)
            fruitList.add(apple)
            val banana = Fruit("Banana",  R2.drawable.ic_btn_speak_now)
            fruitList.add(banana)
            val orange = Fruit("Orange",  R2.drawable.ic_input_add)
            fruitList.add(orange)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_list)

        val colorArrays = resources.getStringArray(R.array.ListColors)
        val arrayAdapter2 = ArrayAdapter(this, android.R.layout.simple_list_item_1, colorArrays)

        initFruits()
//        val arrayAdapter = FruitAdapter(fruitList as ArrayList<Fruit>, this)

        listView.adapter = arrayAdapter2
        listView.dividerHeight = 10

        listView.setOnItemClickListener { adapterView, view, position: Int, id: Long ->
//            val item = colorArrays[position]
//            Log.w("id = ", "$id, $item")
//            toast(item)
            val selectedItem = adapterView.getItemAtPosition(position)
            Log.w("adapterView = ", "$adapterView")
            Log.w("view = ", "$view")
            Log.w("selectedItem = ", "$selectedItem")
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

