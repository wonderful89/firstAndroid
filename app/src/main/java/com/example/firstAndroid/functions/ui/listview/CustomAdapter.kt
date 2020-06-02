package com.example.firstAndroid.functions.ui.listview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.firstAndroid.R


class CustomAdapter(
    private val context: Context,
    dataItems: ArrayList<HashMap<String, Any>>
) :
    BaseAdapter() {
    private val dataItems: ArrayList<HashMap<String, Any>> = dataItems
    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return dataItems.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(
        position: Int,
        convertView: View,
        parent: ViewGroup
    ): View {
        var convertView = convertView
        val holder: ViewHolder
        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.item_simple_list_7_custom_view, null)
            holder = ViewHolder()
            holder.imageView = convertView.findViewById<View>(R.id.imageV) as ImageView
            holder.title = convertView.findViewById<View>(R.id.text_title) as TextView
            holder.content = convertView.findViewById<View>(R.id.text_content) as TextView
            holder.btn = convertView.findViewById<View>(R.id.btn) as Button
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
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
        return convertView
    }

    internal inner class ViewHolder {
        var imageView: ImageView? = null
        var title: TextView? = null
        var content: TextView? = null
        var btn: Button? = null
    }

}