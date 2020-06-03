package com.example.firstAndroid.functions.ui.listview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.firstAndroid.R


class CustomAdapter(
    private val context: Context,
    private val dataItems: ArrayList<HashMap<String, Any>>
) :
    BaseAdapter() {
    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return dataItems.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var convertView2 = convertView
        val holder: ViewHolder
        if (null == convertView2) {
            convertView2 = mInflater.inflate(R.layout.item_simple_list_7_custom_view, null)
            holder = ViewHolder()
            holder.imageView = convertView2.findViewById<View>(R.id.imageV) as ImageView
            holder.title = convertView2.findViewById<View>(R.id.text_title) as TextView
            holder.content = convertView2.findViewById<View>(R.id.text_content) as TextView
            holder.btn = convertView2.findViewById<View>(R.id.btn) as Button
            convertView2.tag = holder
        } else {
            holder = convertView2.tag as ViewHolder
        }
        // 设置数据
        holder.imageView!!.setImageResource(R.mipmap.ic_launcher)
        holder.title!!.text = dataItems[position]["title"].toString()
        holder.content!!.text = dataItems[position]["content"].toString()
        holder.btn!!.setOnClickListener {
            Toast.makeText(
                context,
                "点中了第" + position + "行的按钮",
                Toast.LENGTH_LONG
            ).show()
        }
        return convertView2!!
    }

    override fun getItem(p0: Int): Any? {
        return null
    }

    internal inner class ViewHolder {
        var imageView: ImageView? = null
        var title: TextView? = null
        var content: TextView? = null
        var btn: Button? = null
    }
}