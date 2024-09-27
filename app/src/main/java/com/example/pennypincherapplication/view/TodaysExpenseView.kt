package com.example.pennypincherapplication.view

import com.example.pennypincherapplication.model.Expense

interface TodaysExpenseView {
    fun displayTotalExpense(totalExpense: Long)
    fun displayTodaysExpenses(expenses: List<Expense>)
}
