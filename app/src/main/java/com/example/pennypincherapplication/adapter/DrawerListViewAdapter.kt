package com.example.pennypincherapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.pennypincherapplication.R

class DrawerListViewAdapter(private val context: Context) : BaseAdapter() {

    private val drawerItems: Array<String> = context.resources.getStringArray(R.array.drawer_items)

    override fun getCount(): Int {
        return drawerItems.size
    }

    override fun getItem(position: Int): String {
        return drawerItems[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if (view == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.drawer_list_item, parent, false)
        }

        val item = view?.findViewById<TextView>(R.id.drawer_list_item)
        item?.text = drawerItems[position]

        return view!!
    }
}
