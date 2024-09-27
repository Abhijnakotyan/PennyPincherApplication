package com.example.pennypincherapplication.view

import com.example.pennypincherapplication.model.Expense

interface CurrentWeekExpenseView {
    fun displayCurrentWeeksExpenses(expensesByDate: Map<String, List<Expense>>)

    fun displayTotalExpenses(totalExpense: Long)
}
