package com.example.pennypincherapplication.adapter

import android.content.Context
import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import android.widget.TextView
import com.example.pennypincherapplication.R
import com.example.pennypincherapplication.model.Expense
import com.example.pennypincherapplication.util.ExpenseCollection

class CurrentWeeksExpenseAdapter(
    private val context: Context,
    private val expenses: Map<String, List<Expense>>
) : ExpandableListAdapter {

    override fun registerDataSetObserver(observer: DataSetObserver?) {
        // Optional: Implement if needed
    }

    override fun unregisterDataSetObserver(observer: DataSetObserver?) {
        // Optional: Implement if needed
    }

    override fun getGroupCount(): Int {
        return expenses.keys.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return expenses[expenses.keys.elementAt(groupPosition)]?.size ?: 0
    }

    override fun getGroup(groupPosition: Int): Any {
        val date = expenses.keys.elementAt(groupPosition)
        val totalExpense = ExpenseCollection(expenses[date] ?: emptyList()).totalExpense
        return "$date (${getDayName(date)}) - ${context.getString(R.string.rupee_sym)}$totalExpense"
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        val expense = expenses[expenses.keys.elementAt(groupPosition)]?.get(childPosition)
        return "${expense?.type} - ${expense?.amount}"
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        val parentText = getGroup(groupPosition) as String
        var view = convertView
        if (view == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.expense_header_text_box, parent, false)
        }
        val textBox = view?.findViewById<TextView>(R.id.expense_header_text_box)
        textBox?.text = parentText
        return view!!
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        val childText = getChild(groupPosition, childPosition) as String
        var view = convertView
        if (view == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.expense_text_box, parent, false)
        }
        val textBox = view?.findViewById<TextView>(R.id.expense_text_box)
        textBox?.text = childText
        return view!!
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return false
    }

    override fun areAllItemsEnabled(): Boolean {
        return false
    }

    override fun isEmpty(): Boolean {
        return expenses.isEmpty()
    }

    override fun onGroupExpanded(groupPosition: Int) {
        // Optional: Implement if needed
    }

    override fun onGroupCollapsed(groupPosition: Int) {
        // Optional: Implement if needed
    }

    override fun getCombinedChildId(groupId: Long, childId: Long): Long {
        return 0
    }

    override fun getCombinedGroupId(groupId: Long): Long {
        return 0
    }

    private fun getDayName(date: String): String {
        // Implement this method to return the day name based on the date string
        return "Day Name"  // Placeholder for actual logic
    }
}
