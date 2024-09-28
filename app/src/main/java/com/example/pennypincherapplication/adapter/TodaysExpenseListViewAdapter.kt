package com.example.miniproject.adapter

import android.content.Context
import android.widget.ArrayAdapter
import com.example.pennypincherapplication.model.Expense

class TodaysExpenseListViewAdapter(
    private val expenses: List<Expense>,
    context: Context,
    resource: Int
) : ArrayAdapter<Any>(context, resource) {

    override fun getCount(): Int {
        return expenses.size
    }

    override fun getItem(position: Int): Any {
        val expense = expenses[position]
        return "${expense.type} - ${expense.amount}"
    }

    override fun getItemId(position: Int): Long {
        return expenses[position].id.toLong() // Assuming id is a numeric string
    }

}
